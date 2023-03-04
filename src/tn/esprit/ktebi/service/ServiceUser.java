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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.ktebi.entities.Role;
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
                Date date = java.sql.Date.valueOf(t.getDateNaissance());
       
                String req = "INSERT INTO `utilisateur`( `prenom`, `nom`, `email`,"
                        + " `password`, `tel`, `dateDeNaissance`, `adresse`, `id_role`,`status`)"
                +" VALUES ('"+t.getPrenom()+"', '"+t.getNom()+"','"+t.getEmail()+"','"+t.getMotPasse()+"',"
                        + "'"+t.getTel()+"','"+date+"','"+t.getAdresse()+"','"+t.getRole().getId()+"','"+t.getStatus()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Person ajouté !");
    }

    @Override
    public void updateOne(User t) throws SQLException {
            PreparedStatement st = cnx.prepareStatement("update utilisateur set  prenom=?, nom=?, email=?, password=?, tel=?,"
                    + " dateDeNaissance=?, adresse=? where id_user=? ");
        st.setString(1, t.getPrenom());
        st.setString(2,t.getNom());
        st.setString(3, t.getEmail());
        st.setString(4, t.getMotPasse());
        st.setInt(5,t.getTel());
        st.setDate(6, java.sql.Date.valueOf(t.getDateNaissance()));
        st.setString(7, t.getAdresse());
        st.setInt(8, t.getId());
        st.executeUpdate();
    }

    @Override
    public List<User> selectAll() throws SQLException {
      List<User> user = new ArrayList<>();

        String req = "SELECT * FROM `utilisateur`";

        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){

            User p = new User();
            p.setId(rs.getInt("id_user"));
            p.setPrenom(rs.getString("prenom"));
            p.setNom(rs.getString("nom"));
            p.setEmail(rs.getString("email"));
            p.setMotPasse(rs.getString("password")); 
            p.setTel(rs.getInt("tel"));
            p.setAdresse(rs.getString("adresse"));
            p.setDateNaissance(rs.getDate("dateDeNaissance").toLocalDate());
            p.setStatus(rs.getString("status")); 
            user.add(p);
        }    
        return user;
    }

      public List<Role> RecupCombo() throws SQLException{
             
             
      List<Role> list = new ArrayList<>();
    
          String sql = "SELECT * FROM `role`";

        PreparedStatement ps = cnx.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
    while (rs.next()){
     Role r = new Role();
        r.setId(rs.getInt("id_role"));
        r.setRole(rs.getString("role"));
     
      list.add(r);

    }


    return list;
    }
         public User SelectUser(int id){
          
        User r = new User();
        
        String req = "SELECT * FROM `utilisateur` where id_user ="+id;
        
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery(req);
            
            while(rs.next()){           
                 
               r = new User(rs.getInt("id_user") ,rs.getString("nom"),rs.getString("prenom"),
                       rs.getString("email"),rs.getString("adresse"),rs.getInt("tel"),
                       rs.getString("password"),rs.getDate("dateDeNaissance").toLocalDate());

            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser .class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
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
             System.out.println(id);
             User.connecte = id  ;
         }
            return id;        
    }
    
    public void desactiver(User t) throws SQLException {
            PreparedStatement st = cnx.prepareStatement("update utilisateur set  status=? where id_user=? ");
        st.setString(1,"desactiver");
        st.setInt(2,t.getId());
        st.executeUpdate();        
    }
    
    @Override
    public void deletOne(User t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
