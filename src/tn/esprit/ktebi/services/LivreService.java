/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class LivreService implements ILivreService<Livre> {
    
    
    private Connection cnx ;
    
    public LivreService()
    {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void create(Livre t) throws SQLException {
        String req= " INSERT INTO `livre`(`libelle`, `description`, `id_user`, `editeur`, `categorie`, `date_edition`, `code_promo`, `prix`, `image`, `langue`)"
                + "VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement ps = cnx.prepareStatement(req);
        
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        ps.setString(1, t.getLibelle());
        ps.setString(2, t.getDescription());
        ps.setInt(3, t.getId_user());
        ps.setString(4, t.getEditeur());
        ps.setString(5, t.getCategorie());
        ps.setString(6,date);
        ps.setInt(7, t.getPromo());
        ps.setFloat(8, t.getPrix());
        ps.setString(9, t.getImage());
        ps.setString(10, t.getLangue());
        ps.executeUpdate();  
        System.out.println("Livre ajouté !");
    }

    @Override
    public void update(Livre t) throws SQLException {
        String req= " UPDATE `livre` SET `libelle`=?,`description`=?,`id_user`=?,`editeur`=?,`categorie`=?,`date_edition`=?,`code_promo`=?,`prix`=?,"
                + "`image`=?,`langue`=? WHERE id_livre=?";
        
        PreparedStatement ps = cnx.prepareStatement(req);
        
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        ps.setString(1, t.getLibelle());
        ps.setString(2, t.getDescription());
        ps.setInt(3, t.getId_user());
        ps.setString(4, t.getEditeur());
        ps.setString(5, t.getCategorie());
        ps.setString(6,date);
        ps.setInt(7, t.getPromo());
        ps.setFloat(8, t.getPrix());
        ps.setString(9, t.getImage());
        ps.setString(10, t.getLangue());
        ps.setInt(11, t.getId());
        ps.executeUpdate();  
        System.out.println("Livre modifié !");
    }

    @Override
    public void delete(int id) throws SQLException {
        String req="DELETE FROM `livre` WHERE  id_livre = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,id);
        int row = ps.executeUpdate(); 
        if(row==1)
        {
            System.out.println("Livre supprimé !");
        }
        else
        {
           System.out.println(row); 
        }
    }

    @Override
    public List<Livre> selectAll() throws SQLException {
        List<Livre> temp = new ArrayList<>();
        String req="SELECT * FROM `livre` ";
        
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ResultSet rs = ps.executeQuery() ;
        while(rs.next())
        {
            Livre l = new Livre();
            l.setId(rs.getInt(1));
            l.setLibelle(rs.getString(2));
            l.setDescription(rs.getString(3));
            l.setId_user(rs.getInt(4));
            l.setEditeur(rs.getString(5));
            l.setCategorie(rs.getString(6));
            l.setDate_edition(rs.getDate(7));
            l.setPromo(rs.getInt(8));
            l.setPrix(rs.getFloat(9));
            l.setImage(rs.getString(10));
            l.setLangue(rs.getString(11));
            temp.add(l);
        }
        return temp ;
    }
    
    @Override
    public List<Livre> searchByLibelle(String libelle) throws SQLException {
        List<Livre> temp = new ArrayList<>();
        String req="SELECT * FROM `livre` WHERE libelle=? ";
        
        PreparedStatement ps = cnx.prepareStatement(req);
         ps.setString(1, libelle);
        
        ResultSet rs = ps.executeQuery() ;
        while(rs.next())
        {
            Livre l = new Livre();
            l.setId(rs.getInt(1));
            l.setLibelle(rs.getString(2));
            l.setDescription(rs.getString(3));
            l.setId_user(rs.getInt(4));
            l.setEditeur(rs.getString(5));
            l.setCategorie(rs.getString(6));
            l.setDate_edition(rs.getDate(7));
            l.setPromo(rs.getInt(8));
            l.setPrix(rs.getFloat(9));
            l.setImage(rs.getString(10));
            l.setLangue(rs.getString(11));
            temp.add(l);
        }
        return temp ;
    }
    
    
    
    @Override
    public List<Livre> searchByCategorie(String categorie) throws SQLException {
        List<Livre> temp = new ArrayList<>();
        String req="SELECT * FROM `livre` WHERE categorie=? ";
        
        PreparedStatement ps = cnx.prepareStatement(req);
         ps.setString(1, categorie);
        
        ResultSet rs = ps.executeQuery() ;
        while(rs.next())
        {
            Livre l = new Livre();
            l.setId(rs.getInt(1));
            l.setLibelle(rs.getString(2));
            l.setDescription(rs.getString(3));
            l.setId_user(rs.getInt(4));
            l.setEditeur(rs.getString(5));
            l.setCategorie(rs.getString(6));
            l.setDate_edition(rs.getDate(7));
            l.setPromo(rs.getInt(8));
            l.setPrix(rs.getFloat(9));
            l.setImage(rs.getString(10));
            l.setLangue(rs.getString(11));
            temp.add(l);
        }
        return temp ;
    }
    
}
