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
import java.util.List;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.interfaces.IService;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Dell 6540
 */
public class ServiceReponse implements IService<ReponseReclamation>{
    private Connection cnx;
    public ServiceReponse(){
        cnx = MaConnexion.getInstance().getCnx();
    }
    @Override
    public void createOne(ReponseReclamation rec) throws SQLException {
        PreparedStatement st = cnx.prepareStatement("INSERT INTO reponserec (contenu,id_reclamation,DateRep)VALUES (?, ?, ?)");
        st.setString(1,rec.getContenu());
        st.setInt(2,rec.getReclamation().getId());
        st.setDate(3, Date.valueOf(rec.getDateRep()));
        st.executeUpdate();
        PreparedStatement st1 = cnx.prepareStatement("update reclamation set etat=? where id_rec=?");
        st1.setString(1,"terminé");
        st1.setInt(2,rec.getReclamation().getId());
        st1.executeUpdate();

        System.out.println("Reclamtion ajouté !");
    }
      public ReponseReclamation selectAllById(Integer id) throws SQLException {
        ReponseReclamation liste = new ReponseReclamation();

        String req = "SELECT * FROM reponserec where id_reclamation=?";                  
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            liste.setId(rs.getInt("id_reponse"));            
            liste.setContenu(rs.getString("contenu"));
            liste.setDateRep(rs.getDate("DateRep").toLocalDate());
        }

        return liste;
    }

    @Override
    public List selectAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateOne(ReponseReclamation t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletOne(ReponseReclamation t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}