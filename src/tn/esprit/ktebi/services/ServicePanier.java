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
import java.util.ArrayList;
import java.util.List;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.interfaces.IPanier;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class ServicePanier implements IPanier {

    private Connection cnx;

    public ServicePanier() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    @Override
    public void ajouterPanier(Panier panier) throws SQLException {
        try {
            String requete = "insert into panier(id_user, qte, mnt_total) values (?, ?, ?)";
            PreparedStatement st = cnx.prepareStatement(requete);
            
            st.setInt(1, panier.getUser().getId());
            st.setInt(2, panier.getQte());
            st.setFloat(3, panier.getMontant_totale());
            st.executeUpdate();
            System.out.println("Le panier est ajouté avec succèe!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierPanier(Panier panier) throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("UPDATE panier SET mnt_total=?, qte=? WHERE id_panier=?");
        ps.setFloat(1, panier.getMontant_totale());
        ps.setInt(2, panier.getQte());
        ps.setInt(3, panier.getId());
        ps.executeUpdate();
        ps.close();
    }

    @Override
    public Panier getPanierByUser(int userId) throws SQLException {
        Panier panier = null;
        String requete = "SELECT * FROM panier WHERE id_user = ?";

        try {
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_panier");
                int Id = rs.getInt("id_user");
                User user = new User();
                user.setId(Id);
                
                int qte = rs.getInt("qte");
                float montantTotale = rs.getFloat("mnt_total");
                panier = new Panier(id, montantTotale, qte, user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return panier;
    }

//    @Override
//    public void ViderPanier(int id_user) throws SQLException {
//        try {
//            cnx.setAutoCommit(false); // désactiver l'autocommit
//
//            String requete2 = "DELETE FROM ligne_panier WHERE id_panier IN (SELECT id_panier FROM panier WHERE id_user = ?)";
//            PreparedStatement st2 = cnx.prepareStatement(requete2);
//            st2.setInt(1, id_user);
//            st2.executeUpdate();
//
//
//            String requete3 = "DELETE FROM panier WHERE id_user = ?";
//            PreparedStatement st3 = cnx.prepareStatement(requete3);
//            st3.setInt(1, id_user);
//            st3.executeUpdate();
//
//            cnx.commit(); // valider la suppression du panier
//
//            System.out.println("Le panier est vidé!!");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            cnx.rollback();
//        } finally {
//            try {
//                cnx.setAutoCommit(true); // réactiver l'autocommit
//            } catch (SQLException ex) {
//                System.out.println("Erreur lors de la réactivation de l'autocommit : " + ex.getMessage());
//            }
//        }
//    }
    @Override
    public List<Panier> afficherPanier() throws SQLException {
        List<Panier> listPanier = new ArrayList<>();

        String requete = "SELECT * FROM `panier`";
        PreparedStatement ps = cnx.prepareStatement(requete);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Panier p = new Panier();
            p.setId(rs.getInt(1));
            p.setQte(rs.getInt("qte"));
            p.setMontant_totale(rs.getFloat("mnt_total"));
            int userId = rs.getInt("id_user");
            User user = new User();
            user.setId(userId);
            p.setUser(user);
            
            

            listPanier.add(p);

        }

        return listPanier;
    }

    @Override
    public String getLibelleLivreById(int id_livre) throws SQLException {
        String libelle = null;
        try {
            String requete = "select libelle from livre where id_livre = ?";
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setInt(1, id_livre);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                libelle = rs.getString("libelle");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return libelle;
    }

    @Override
    public Livre getLivreByLibelle(String libelle) throws SQLException {
        String requete = "SELECT * FROM livre WHERE libelle = ?";
        Livre livre = new Livre();
        try {
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setString(1, libelle);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                livre.setId(rs.getInt("id_livre"));
                livre.setLibelle(rs.getString("libelle"));
                livre.setDescription(rs.getString("description"));
                livre.setEditeur(rs.getString("editeur"));
                livre.setDate_edition(rs.getString("date_edition"));
                livre.setCategorie(rs.getString("categorie"));
                livre.setPrix((float) rs.getDouble("prix"));
                livre.setLangue(rs.getString("langue"));
                livre.setPromo(rs.getInt("code_promo"));
                livre.setImage(rs.getString("image"));
                int userId = rs.getInt("id_user");
                User user = new User();
                user.setId(userId);
                livre.setUser(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return livre;
    }

}
