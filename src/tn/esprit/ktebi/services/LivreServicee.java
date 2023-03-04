/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class LivreServicee implements ILivreService<Livre> {
    
    private Connection cnx ;
    
    public LivreServicee()
    {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void create(Livre t) throws SQLException {
        String req = "INSERT INTO `livre` (libelle, description, auteur, editeur, categorie, date_edition, code_promo, prix, langue ,image ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        
        
            ResultSet rs = null;
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, t.getLibelle());
            ps.setString(2,t.getDescription());
            ps.setInt(3,t.getAuteur().getId()); // suppose que la classe User a un attribut id
            ps.setString(4, t.getEditeur());
            ps.setString(5, t.getCategorie());
            ps.setDate(6, new java.sql.Date(t.getDate_edition().getTime())); // conversion de java.util.Date à java.sql.Date
            ps.setInt(7, t.getPromo().getId()); // suppose que la classe Promo a un attribut id
            ps.setDouble(8, t.getPrix());
            ps.setString(9, t.getLangue());
            
            ps.setString(10, t.getImage());
            
            
            

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating livre failed, no rows affected.");
            }
            else
            {
                System.out.println("Livre ajouté");
            }

            
}
    
    // méthode utilitaire pour convertir l'image en tableau d'octets (byte[])
    private byte[] imageToBytes(Image image) throws IOException {
        // implémentation de la conversion d'image en tableau d'octets
        if (image == null) {
            return null;
        }

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        baos.flush();

        byte[] bytes = baos.toByteArray();
        baos.close();

        return bytes;
    }
    
    
    

    @Override
    public void update(Livre t) throws SQLException {
        String req = "UPDATE livre SET libelle=?, "
                + "description=?, auteur=?, editeur=?, "
                + "categorie=?, date_edition=?, code_promo=?, prix=?, "
                + "langue=? , image=?  WHERE id_livre=?";
        
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getLibelle());
        ps.setString(2, t.getDescription());
        ps.setInt(3, t.getAuteur().getId());
        ps.setString(4, t.getEditeur());
        ps.setString(5, t.getCategorie());
        ps.setDate(6, new java.sql.Date(t.getDate_edition().getTime()));
        ps.setInt(7, t.getPromo().getId());
        ps.setDouble(8, t.getPrix());
        ps.setString(9, t.getLangue());
       
        ps.setString(10, t.getImage());
       
        ps.setInt(11, t.getId());
        
        int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Livre modifié !");
            }
    }
    



    @Override
    public void delete(int id) throws SQLException {
     
    
    String req = "DELETE FROM livre WHERE id_livre = ?";
    
    
        
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        
        int rowsAffected = ps.executeUpdate();
        
        if(rowsAffected ==1)
        {
            System.out.println("Livre supprimé !");
        }
        else
        {
           System.out.println(rowsAffected); 
        }


    }

    @Override
    public List<Livre> selectAll() throws SQLException {
        List<Livre> livres = new ArrayList<>();
       
        
        String req ="SELECT livre.*, utilisateur.nom as nom_auteur, utilisateur.prenom  as prenom_auteur, promo.* "
                + "FROM `livre`  "
                + "JOIN utilisateur ON livre.auteur = utilisateur.id_user  "
                + "JOIN promo  ON livre.code_promo = promo.id" ;
        
        PreparedStatement ps = cnx.prepareStatement(req);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Livre livre = new Livre();
            livre.setId(rs.getInt("id_livre"));
            livre.setLibelle(rs.getString("libelle"));
            livre.setDescription(rs.getString("description"));
            livre.setEditeur(rs.getString("editeur"));
            livre.setCategorie(rs.getString("categorie"));
            livre.setDate_edition(rs.getDate("date_edition"));
            
            livre.setPrix(rs.getFloat("prix"));
            livre.setLangue(rs.getString("langue"));
            //livre.setImage(rs.getBytes("image"));
            
            User auteur = new User();
            auteur.setId(rs.getInt("auteur"));
            auteur.setNom(rs.getString("nom_auteur"));
            auteur.setPrenom(rs.getString("prenom_auteur"));
            livre.setAuteur(auteur);
            
            Promo promo = new Promo();
            promo.setId(rs.getInt("id"));
            promo.setCode(rs.getString("code"));
            promo.setReduction(rs.getDouble("reduction"));
            promo.setDate_debut(rs.getDate("date_debut"));
            promo.setDate_fin(rs.getDate("date_fin"));
            
            livre.setPromo(promo);
            
            livres.add(livre);
        }


        return livres;
}


    @Override
    public List<Livre> searchByLibelle(String libelle) throws SQLException {
        
    List<Livre> livres = new ArrayList<>();
    
    UserService us = new UserService();
    
    PromoService prs = new PromoService();
    
    String req = "SELECT * FROM livre WHERE libelle LIKE ?";
    
   
        PreparedStatement ps = cnx.prepareStatement(req); 
        ps.setString(1, "%" + libelle + "%");
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id_livre"));
                livre.setLibelle(rs.getString("libelle"));   
                livre.setDescription(rs.getString("description"));
                livre.setAuteur(us.getUserById(rs.getInt("auteur")));
                livre.setEditeur(rs.getString("editeur"));
                livre.setCategorie(rs.getString("categorie"));
                livre.setDate_edition(rs.getDate("date_edition"));
                livre.setPromo(prs.selectById(rs.getInt("code_promo")));
                livre.setPrix(rs.getFloat("prix"));
                //livre.setImage(rs.getBytes("image"));
                livre.setLangue(rs.getString("langue"));
                livres.add(livre);
            }
    return livres;


    }

    @Override
    public List<Livre> searchByCategorie(String categorie) throws SQLException {
        List<Livre> livres = new ArrayList<>();
    
    UserService us = new UserService();
    
    PromoService prs = new PromoService();
    
    String req = "SELECT * FROM livre WHERE categorie LIKE ?";
   
        PreparedStatement ps = cnx.prepareStatement(req); 
        ps.setString(1, "%" + categorie + "%");
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Livre livre = new Livre();
                livre.setId(rs.getInt("id_livre"));
                livre.setLibelle(rs.getString("libelle"));   
                livre.setDescription(rs.getString("description"));
                livre.setAuteur(us.getUserById(rs.getInt("auteur")));
                livre.setEditeur(rs.getString("editeur"));
                livre.setCategorie(rs.getString("categorie"));
                livre.setDate_edition(rs.getDate("date_edition"));
                livre.setPromo(prs.selectById(rs.getInt("code_promo")));
                livre.setPrix(rs.getFloat("prix"));
                //livre.setImage(rs.getBytes("image"));
                livre.setLangue(rs.getString("langue"));
                livres.add(livre);
            }
    return livres;
    }






    }

   