/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.Connexion;

/**
 *
 * @author Dell 6540
 */
public class ServiceReclamation implements IService<Reclamation> {
    private Connection cnx;
    private FileInputStream fis;
    public ServiceReclamation(){
        cnx = Connexion.getInstance().getCnx();
    }
    @Override
    public void createOne(Reclamation r) throws SQLException {
        try {
            PreparedStatement st = cnx.prepareStatement("INSERT INTO reclamation (id_user, contenu,"
                    + " date_rec, etat, img, type_rec) VALUES (?, ?, ?, ?, ?,?)");
            st.setInt(1,r.getUser().getId());
            st.setString(2,r.getContenu());
            st.setDate(3, Date.valueOf(r.getDate_reclamation()));
            st.setString(4,r.getEtat());
            fis = new FileInputStream(r.getImg());
            st.setBinaryStream(5,(InputStream)fis,r.getImg().length());
            st.setInt(6,r.getType().getId());            
            st.executeUpdate();
            System.out.println("Reclamtion ajouté !");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateOne(Reclamation r) throws SQLException {
        PreparedStatement st = cnx.prepareStatement("update reclamation set id_user =?, contenu=?, date_rec=?,"
                + " etat=? where id_rec =?");
        st.setInt(1, r.getUser().getId());
        st.setString(2, r.getContenu());
        st.setDate(3, Date.valueOf(r.getDate_reclamation()));
        st.setString(4, r.getEtat());
        st.setInt(5, r.getId());
        st.executeUpdate();

    }

    @Override
    public void deletOne(Reclamation r) throws SQLException {
        Integer id =r.getId();
        PreparedStatement st = cnx.prepareStatement("delete from reclamation where id_rec =?");
        st.setInt(1,id);
        st.executeUpdate();
    }

    @Override
    public List<Reclamation> selectAll() throws SQLException {
        List<Reclamation> liste = new ArrayList<>();

        String req = "SELECT * FROM reclamation";
        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            User u = new User();
            ReponseReclamation r= new ReponseReclamation();
            Reclamation rec = new Reclamation();
            rec.setId(rs.getInt("id_rec"));            
            rec.setContenu(rs.getString("contenu"));
            rec.setDate_reclamation(rs.getDate("date_rec").toLocalDate());
            rec.setEtat(rs.getString("etat"));
            rec.setUser(u);

            liste.add(rec);
        }

        return liste;
    }
      public List<Reclamation> selectAllById(Integer id) throws SQLException {
        List<Reclamation> liste = new ArrayList<>();

        String req = "SELECT * FROM reclamation where id_user=?";                  
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            User u = new User();
            ReponseReclamation r= new ReponseReclamation();
            Reclamation rec = new Reclamation();
            rec.setId(rs.getInt("id_rec"));            
            rec.setContenu(rs.getString("contenu"));
            rec.setDate_reclamation(rs.getDate("date_rec").toLocalDate());
            rec.setEtat(rs.getString("etat"));
            rec.setUser(u);
            /*rec.setImg1(rs.getBlob("img"));*/

            liste.add(rec);
        }

        return liste;
    }
      
    public List<Reclamation> selectAllOrderByDate() throws SQLException {
        List<Reclamation> liste = new ArrayList<>();

        String req = "SELECT * FROM reclamation order by date_rec";
        PreparedStatement ps = cnx.prepareStatement(req);

        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            User u = new User();
            ReponseReclamation r= new ReponseReclamation();
            Reclamation rec = new Reclamation();
            rec.setId(rs.getInt("id_rec"));            
            rec.setContenu(rs.getString("contenu"));
            rec.setDate_reclamation(rs.getDate("date_rec").toLocalDate());
            rec.setEtat(rs.getString("etat"));
            rec.setUser(u);

            liste.add(rec);
        }

        return liste;
    }  
      
      
}
