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
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.TypeReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Dell 6540
 */
public class ServiceTypeReclamation {
      private Connection cnx;
    public ServiceTypeReclamation(){
        cnx = MaConnexion.getInstance().getCnx();  
    }
    public List selectAll() throws SQLException {
        List<TypeReclamation> liste = new ArrayList<>();

        String req = "SELECT * FROM type_rec";
        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            User u = new User();
            TypeReclamation tr= new TypeReclamation();
            tr.setId(rs.getInt("id_type"));            
            tr.setType(rs.getString("type"));

            liste.add(tr);
        }

        return liste;    
    }
        public TypeReclamation selectAllByType(String type) throws SQLException {
                    TypeReclamation tr= new TypeReclamation();

            String req = "SELECT * FROM type_rec where type=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, type);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            User u = new User();
            tr.setId(rs.getInt("id_type"));            
            tr.setType(rs.getString("type"));

        }

        return tr;    
    }
}