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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.ktebi.interfaces.ILignePanier;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class ServiceLignePanier implements ILignePanier{
    private Connection cnx;

    public ServiceLignePanier() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    @Override
    public void ajouterLignePanier(LignePanier p) {
        try {
            String requete = "INSERT INTO ligne_panier(id_livre, id_panier, qte) VALUES (?, ?, ?);";
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setInt(1, p.getLivre().getId());
            st.setInt(2, p.getPanier().getId());
            st.setInt(3, p.getQuantite());
            st.executeUpdate();
            System.out.println("La ligne du panier est ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifierLignePanier(LignePanier p) {
        try {
            String requete = "update ligne_panier set qte = ? where id_livre = ? and id_panier = ?";
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setInt(1, p.getQuantite());
            st.setInt(2, p.getLivre().getId());
            st.setInt(3, p.getPanier().getId());
            st.executeUpdate();
            System.out.println("La ligne du panier a été mise à jour avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public LignePanier getLignePanierByLivreAndPanier(Livre livre, Panier panier) throws SQLException {
        LignePanier lignePanier = null;
        String requete = "SELECT * FROM ligne_panier WHERE id_livre = ? AND id_panier = ?";

        try (PreparedStatement st = cnx.prepareStatement(requete)) {

            st.setInt(1, livre.getId());
            st.setInt(2, panier.getId());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_ligne");
                    int qte = rs.getInt("qte");

                    // créer une nouvelle instance de LignePanier
                    lignePanier = new LignePanier(id, livre, panier, qte);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lignePanier;
    }

    @Override
    public void supprimerLignePanier(Livre livre) {
        try {
            String lignePanierQuery = "SELECT id_ligne FROM ligne_panier WHERE id_livre=?";
            PreparedStatement lignePanierStmt = cnx.prepareStatement(lignePanierQuery);
            lignePanierStmt.setInt(1, livre.getId());
            ResultSet lignePanierRs = lignePanierStmt.executeQuery();

            if (lignePanierRs.next()) {
                int lignePanierId = lignePanierRs.getInt("id_ligne");
                String deleteQuery = "DELETE FROM ligne_panier WHERE id_ligne=?";
                PreparedStatement deleteStmt = cnx.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, lignePanierId);
                deleteStmt.executeUpdate();
                System.out.println("La ligne du panier est supprimée!");
            } else {
                System.out.println("La ligne de panier correspondant à l'ID du livre n'existe pas.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<LignePanier> getLignePanierByLivre(Livre livre, Panier p) throws SQLException {
        List<LignePanier> lignes = new ArrayList<>();

        String query = "SELECT * FROM ligne_panier WHERE id_livre=? AND id_panier=?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setInt(1, livre.getId());
        stmt.setInt(2, p.getId()); 

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            LignePanier ligne = new LignePanier();
            ligne.setId(rs.getInt("id_ligne"));
            
            //panier
            int panier_id = rs.getInt("id_panier");
            Panier panier = new Panier();
            panier.setId(panier_id);
            ligne.setPanier(panier);
               
            //livre
            int livre_id = rs.getInt("id_livre");
            Livre liv = new Livre();
            liv.setId(livre_id);
            ligne.setLivre(liv);
            ligne.setQuantite(rs.getInt("qte"));

            lignes.add(ligne);
        }

        return lignes;
    }

    @Override
    public List<LignePanier> afficherLignePanier() throws SQLException {
        List<LignePanier> listLigne = new ArrayList<>();

        String requete = "SELECT * FROM `ligne_panier`";
        PreparedStatement ps = cnx.prepareStatement(requete);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            LignePanier p = new LignePanier();
            int livre_id = rs.getInt("id_livre");
            Livre liv = new Livre();
            liv.setId(livre_id);
            p.setLivre(liv);
            p.setQuantite(rs.getInt("qte"));

            listLigne.add(p);

        }

        return listLigne;

    }

    @Override
    public Float calculerPrixTotal(int id_panier) throws SQLException {
        float prixTotal = 0.0f;
        String requete = "SELECT * FROM ligne_panier WHERE id_panier = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id_panier);
            ResultSet rsLigne = ps.executeQuery();

            while (rsLigne.next()) {
                int idLivre = rsLigne.getInt("id_livre");
                int qte = rsLigne.getInt("qte");

                String reqLivre = "SELECT prix FROM livre WHERE id_livre = ?";
                PreparedStatement psLivre = cnx.prepareStatement(reqLivre);
                psLivre.setInt(1, idLivre);
                ResultSet rsLivre = psLivre.executeQuery();

                if (rsLivre.next()) {
                    float prixLivre = rsLivre.getFloat("prix");

                    // Calculer le prix total de la ligne de commande
                    prixTotal += qte * prixLivre;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return prixTotal;
    }

    @Override
    public List<LignePanier> getLigneByUser(int id_user) throws SQLException {
        List<LignePanier> lignesCommande = new ArrayList<>();
        String requete = "SELECT lp.*, l.libelle, l.prix FROM ligne_panier lp JOIN livre l "
                + "ON lp.id_livre = l.id_livre WHERE lp.id_panier IN (SELECT p.id_panier FROM panier p WHERE p.id_user = ?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ps.setInt(1, id_user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                //recupérer livre
                int livre_id = rs.getInt("id_livre");
                Livre liv = new Livre();
                liv.setId(livre_id);
                // récupérer panier
                int panier_id = rs.getInt("id_panier");
                Panier panier = new Panier();
                panier.setId(panier_id);
                LignePanier lignePanier = new LignePanier(
                        rs.getInt("id_ligne"),
                        liv,
                        panier,
                        rs.getInt("qte")
                );
                lignesCommande.add(lignePanier);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lignesCommande;
    }

    @Override
    public ObservableList<Livre> listelivres() throws SQLException {
        ObservableList<Livre> myList = FXCollections.observableArrayList();
        try {

            String requete = "Select * FROM Livre";
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Livre rec = new Livre();
                rec.setId(rs.getInt(1));
                rec.setCategorie(rs.getString("categorie"));
                rec.setDate_edition(rs.getDate("date_edition"));
                rec.setDescription(rs.getString("description"));
                rec.setEditeur(rs.getString("editeur"));
                rec.setImage(rs.getString("image"));
                rec.setPrix(rs.getFloat("prix"));
                rec.setLangue(rs.getString("langue"));
                int promoId = rs.getInt("code_promo");
                Promo promo = new Promo();
                promo.setId(promoId);
                rec.setPromo(promo);
                rec.setLibelle(rs.getString("libelle"));
                int userId = rs.getInt("auteur");
                User user = new User();
                user.setId(userId);
                rec.setAuteur(user);

                myList.add(rec);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return myList;
    }
    
}
