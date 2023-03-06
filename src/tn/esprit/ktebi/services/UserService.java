/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.Role;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.interfaces.IService;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class UserService implements IService<User>{

    private Connection cnx;

    public UserService() {
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

                User user = new User(userId, nom, prenom, email);

                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Connection connection = MaConnexion.getInstance().getCnx();
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM user");
        while (result.next()) {
            int id = result.getInt("id_user");
            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            String email = result.getString("email");
            User user = new User(id, nom, prenom, email);
            userList.add(user);
        }
        return userList;
    }
    
    
    public void createOne(User t) throws SQLException {
                Date date = java.sql.Date.valueOf(t.getDateNaissance());
       
                String req = "INSERT INTO `utilisateur`( `prenom`, `nom`, `email`,"
                        + " `password`, `tel`, `dateDeNaissance`, `adresse`, `id_role`,`status`)"
                +" VALUES ('"+t.getPrenom()+"', '"+t.getNom()+"','"+t.getEmail()+"','"+t.getMotPasse()+"',"
                        + "'"+t.getTel()+"','"+date+"','"+t.getAdresse()+"','"+t.getRole().getId()+"','"+t.getStatus()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("Person ajoutÃ© !");
    }

 
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

  
    public List<User> selectAll() throws SQLException {
      List<User> user = new ArrayList<>();

        String req = "SELECT utilisateur.*,role.role as nomrole,role.*"
                +"FROM `utilisateur`"
                +"JOIN role on utilisateur.id_role = role.id_role ";

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
            
            Role r = new Role();
            r.setId(rs.getInt("id_role"));
            r.setRole(rs.getString("nomrole"));
            p.setRole(r);
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
            Logger.getLogger(UserService .class.getName()).log(Level.SEVERE, null, ex);
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
    
        public Boolean  VerifMail(String email)throws SQLException{
        Boolean test = false ;
        String req = "SELECT * FROM utilisateur where email=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
         if(rs.next()){
            test=true;
         }
            return test;        
    }
        
            public Boolean  VerifStatus(String email)throws SQLException{
        Boolean test = false ;
        String req = "SELECT * FROM utilisateur where email=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
         if(rs.next()){
             if(rs.getString("status").equals("desactiver")){
            test=true;
             }
         }
            return test;        
    }
    
    public void desactiver(User t) throws SQLException {
            PreparedStatement st = cnx.prepareStatement("update utilisateur set  status=? where id_user=? ");
        st.setString(1,"desactiver");
        st.setInt(2,t.getId());
        st.executeUpdate();        
    }
    
    
    public void deletOne(User t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
