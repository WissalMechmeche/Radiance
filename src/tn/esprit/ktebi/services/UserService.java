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
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class UserService {
    
    private Connection cnx ;
    
    public UserService()
    {
        cnx = MaConnexion.getInstance().getCnx();
    }


    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
       
        
        String req = "SELECT utilisateur.* FROM utilisateur "
                + "JOIN role ON utilisateur.id_role = role.id_role "
                + "WHERE role.role = 'auteur';";
                
        
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User user = new User();
            
            user.setId(rs.getInt("id_user"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            
            users.add(user);
            
            
    }
        return users;
    
}
    
    public List<User> getAllClients() throws SQLException {
        List<User> users = new ArrayList<>();
       
        
        String req = "SELECT utilisateur.* FROM utilisateur "
                + "JOIN role ON utilisateur.id_role = role.id_role "
                + "WHERE role.role = 'client';";
                
        
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User user = new User();
            
            user.setId(rs.getInt("id_user"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            
            users.add(user);
            
            
    }
        return users;
    
}
    
    public User getUserById(int id) {
    try {
       
        String query = "SELECT * FROM utilisateur WHERE id_user = ?";
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userId = rs.getInt("id_user");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String email = rs.getString("email");
            
            User user = new User(userId, nom, prenom,email);
            
            return user;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return null;
}







}
