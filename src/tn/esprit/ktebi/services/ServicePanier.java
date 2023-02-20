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
//        String requete = "INSERT INTO `panier`(`id_user`, `id_facture`, `qte`,`mnt_total`)"
//                + " VALUES ((select id_user from utilisateur where id_user=?),"
//                + "(select id_facture from facture where id_facture=?), ?,?)";

            String requete = "insert into panier(id_user,id_facture,qte,mnt_total) values(" + panier.getUser() + ",' " + panier.getFacture() + "','" + panier.getQte() + "','"
                    + panier.getMontant_totale() + "');";
            
            PreparedStatement st = cnx.prepareStatement(requete);
            st.executeUpdate(requete);
            System.out.println("Le panier est ajouté avec succèe!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
//    @Override
//    public void modifierQuantitePanier(int id_panier, int id_livre, int qte) throws SQLException {
//        try {
//            String requete = "UPDATE ligne_panier SET qte = ? WHERE id_panier = ? AND id_livre = ?";
//            PreparedStatement st = cnx.prepareStatement(requete);
//            st.setInt(1, qte);
//            st.setInt(2, id_panier);
//            st.setInt(3, id_livre);
//            st.executeUpdate(requete);
//            
//            ServiceLignePanier lig = new ServiceLignePanier();
//            float prixTotal = lig.calculerPrixTotal(id_panier);
//            String requeteChangerPrix = "UPDATE panier SET mnt_total = ? WHERE id_panier = ?";
//            PreparedStatement stPx = cnx.prepareStatement(requeteChangerPrix);
//            stPx.setFloat(1, prixTotal);
//            stPx.setInt(2, id_panier);
//            stPx.executeUpdate();
//            
//            System.out.println("La quantité est modifié!!!");
//            System.out.println("Le nouveau prix est:" + prixTotal);
//            
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        
//    }
    
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
                int user = rs.getInt("id_user");
                int facture = rs.getInt("id_facture");
                int qte = rs.getInt("qte");
                float montantTotale = rs.getFloat("mnt_total");
                panier = new Panier(id, montantTotale, qte, user, facture);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return panier;
    }
    
    @Override
    public void ViderPanier(int id_user) {
        try {
            String requete = "DELETE FROM ligne_panier WHERE id_panier IN (SELECT id_panier FROM panier WHERE id_user = ?)";
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setInt(1, id_user);
            st.executeUpdate();
            System.out.println("Le panier est vidé!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
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
            p.setUser(rs.getInt("id_user"));
            p.setFacture(rs.getInt("id_facture"));
            
            listPanier.add(p);
            
        }
        
        return listPanier;
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
                livre.setUser(rs.getInt("id_user"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return livre;
    }
    
}
