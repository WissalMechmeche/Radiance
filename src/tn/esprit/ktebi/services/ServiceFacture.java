/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.entities.LigneFacture;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.interfaces.IFacture;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class ServiceFacture implements IFacture {

    private Connection cnx;

    public ServiceFacture() {
        cnx = MaConnexion.getInstance().getCnx();
        try {
            cnx.setAutoCommit(false); // désactiver autocommit
        } catch (SQLException ex) {
            System.out.println("Erreur lors du changement de mode d'autocommit de la connexion : " + ex.getMessage());
        }

    }

    @Override
    public void ajouterLigneFacture(LigneFacture ligneFac) throws SQLException {
        try {
            String requete = "insert into ligne_facture(id_facture, id_livre, id_user,mnt,qte) values (?, ?, ?,?,?)";
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setInt(1, ligneFac.getId_facture());
            st.setInt(2, ligneFac.getId_livre());
            st.setInt(3, ligneFac.getId_user());
            st.setFloat(4, ligneFac.getMnt());
            st.setInt(5, ligneFac.getQte());
            st.executeUpdate();
            System.out.println("Ligne Facture ajouté avec succèe!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ajouterFacture(int id_user) throws SQLException {
        try {
            cnx.setAutoCommit(false); // Démarrer la transaction

            // Récupérer le panier de l'utilisateur
            ServicePanier sp = new ServicePanier();
            Panier panier = sp.getPanierByUser(id_user);
            if (panier == null) {
                System.out.println("Aucun panier trouvé pour l'utilisateur " + id_user);
                return;
            }

            // Calculer le montant total de la facture
            ServiceLignePanier lp = new ServiceLignePanier();
            float montant_total = lp.calculerPrixTotal(panier.getId());

            // Ajouter la facture dans la table facture
            String requete1 = "INSERT INTO facture (mode_paiement, mnt_totale, id_user) VALUES (?, ?, ?)";
            PreparedStatement st1 = cnx.prepareStatement(requete1, Statement.RETURN_GENERATED_KEYS);
            st1.setString(1, "Espèce");
            st1.setFloat(2, montant_total);
            st1.setInt(3, id_user);
            st1.executeUpdate();

            // Récupérer l'id de la facture ajoutée
            ResultSet rs1 = st1.getGeneratedKeys();
            int factureId = 0;
            if (rs1.next()) {
                factureId = rs1.getInt(1);
            }
            System.out.println("Facture ajoutée avec succès, id: " + factureId);

            // Ajouter les lignes de facture dans la table ligne_facture
            String requete2 = "INSERT INTO ligne_facture (id_facture, id_livre, id_user, mnt, qte) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st2 = cnx.prepareStatement(requete2);
            String requete3 = "SELECT lp.qte, l.prix, lp.id_livre FROM ligne_panier lp JOIN livre l ON lp.id_livre = l.id_livre WHERE lp.id_panier = ?";
            PreparedStatement st3 = cnx.prepareStatement(requete3);
            st3.setInt(1, panier.getId());
            ResultSet rs3 = st3.executeQuery();
            while (rs3.next()) {
                int livreId = rs3.getInt("id_livre");
                int quantite = rs3.getInt("qte");
                float montant = rs3.getFloat("prix") * quantite;
                st2.setInt(1, factureId);
                st2.setInt(2, livreId);
                st2.setInt(3, id_user);
                st2.setFloat(4, montant);
                st2.setInt(5, quantite);
                st2.executeUpdate();
                System.out.println("Ligne Facture ajoutée avec succès!");
            }

            String requete4 = "DELETE FROM ligne_panier WHERE id_panier = ?";
            PreparedStatement st4 = cnx.prepareStatement(requete4);
            st4.setInt(1, panier.getId());
            st4.executeUpdate();

            String requete5 = "DELETE FROM panier WHERE id_user = ?";
            PreparedStatement st5 = cnx.prepareStatement(requete5);
            st5.setInt(1, id_user);
            st5.executeUpdate();

            cnx.commit(); // Terminer la transaction
            System.out.println("La facture est ajoutée avec succès!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            cnx.rollback(); // Annuler la transaction en cas d'erreur
        } finally {
            cnx.setAutoCommit(true); // Réactiver l'autocommit
        }
    }

    @Override
    public List<Facture> afficherFactures() throws SQLException {
        List<Facture> listFact = new ArrayList<>();

        String requete = "SELECT * FROM `facture`";
        PreparedStatement ps = cnx.prepareStatement(requete);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Facture facture = new Facture();
            facture.setId(rs.getInt(1));
            facture.setMode_paiement(rs.getString("mode_paiement"));
            facture.setMontant_totale(rs.getFloat("mnt_totale"));
            facture.setUser(rs.getInt("id_user"));
            facture.setPanier(rs.getInt("id_panier"));

            listFact.add(facture);

        }

        return listFact;
    }

    @Override
    public void supprimerFacture(Facture f) throws SQLException {
        String requete = "delete from facture where id_facture=" + f.getId() + "";
        try {
            PreparedStatement st = cnx.prepareStatement(requete);
            st.executeUpdate();
            System.out.println("La facture est supprimée!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public User getUserById(int id) throws SQLException {
        PreparedStatement statement = cnx.prepareStatement(
                "SELECT * FROM utilisateur WHERE id_user = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id_user"));
            user.setNom(resultSet.getString("nom"));
            user.setPrenom(resultSet.getString("prenom"));
            return user;
        } else {
            return null;
        }
    }

}
