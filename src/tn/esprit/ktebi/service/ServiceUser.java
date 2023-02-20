/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.MaConnexion;
/**
 *
 * @author Admin
 */
public class ServiceUser implements IService<User>{

        private Connection cnx;

    public ServiceUser(){
        cnx = MaConnexion.getInstance().getCnx();
    }
    @Override
    public void createOne(User t) throws SQLException {
                System.out.println("Person ajouté !");
                Date date = java.sql.Date.valueOf(t.getDateNaissance());
       
                String req = "INSERT INTO `utilisateur`( `prenom`, `nom`, `email`,"
                        + " `password`, `tel`, `dateDeNaissance`, `adresse`, `id_role`)"
                +" VALUES ('"+t.getPrenom()+"', '"+t.getNom()+"','"+t.getEmail()+"','"+t.getMotPasse()+"',"
                        + "'"+t.getTel()+"','"+date+"','"+t.getAdresse()+"',"
                        + "'"+1+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Person ajouté !");
    }

    @Override
    public void updateOne(User t) throws SQLException {
            PreparedStatement st = cnx.prepareStatement("update set utilisateur prenom=?, nom=?, email=?, password=?, tel=?,"
                    + " dateDeNaissance=?, adresse=? where id_user=? ");
        st.setString(1, t.getNom());
        st.setString(2,t.getPrenom());
        st.setString(3, t.getEmail());
        st.setString(4, t.getMotPasse());
        st.setInt(5,t.getTel());
        st.setDate(6, java.sql.Date.valueOf(t.getDateNaissance()));
        st.setString(7, t.getAdresse());
        st.setInt(8, t.getId());
        st.executeUpdate();
    }

    @Override
    public void deletOne(User t) throws SQLException {
    }

    @Override
    public List<User> selectAll() throws SQLException {
      List<User> user = new ArrayList<>();

        String req = "SELECT * FROM `utilisateur`";

        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){

            User p = new User();
            p.setPrenom(rs.getString("prenom"));
            p.setNom(rs.getString("nom"));
            p.setEmail(rs.getString("email"));
            p.setMotPasse(rs.getString("password"));
            p.setTel(rs.getInt("tel"));
            p.setAdresse(rs.getString("adresse"));
            p.setDateNaissance(rs.getDate("dateDeNaissance").toLocalDate());
            user.add(p);
        }    
        return user;
    }
    public User SelectUser(Integer id)throws SQLException{
        
        User user = new User();
        String req = "SELECT * FROM `utilisateur` where id_user=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,id);

        ResultSet rs = ps.executeQuery();
         while(rs.next()){
            user.setPrenom(rs.getString("prenom"));
            user.setNom(rs.getString("nom"));
            user.setEmail(rs.getString("email"));
            user.setMotPasse(rs.getString("password"));
            user.setTel(rs.getInt("tel"));
            user.setAdresse(rs.getString("adresse"));
            user.setDateNaissance(rs.getDate("dateDeNaissance").toLocalDate());
         }
            return user;
        
    }
    public Integer Login(String email,String pwd)throws SQLException{
        Integer id = null ;
        String req = "SELECT * FROM utilisateur where email=? and password=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,email);
        ps.setString(2,pwd);
        ResultSet rs = ps.executeQuery();
         if(rs.next()){
        id= rs.getInt("id_user");
         }
            return id;        
    }
}
