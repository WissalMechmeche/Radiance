package tn.esprit.ktebi.services;

import java.awt.Event;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.ktebi.entities.Evenement;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.utils.MaConnexion;


/**
 *
 * @author ASUS
 */
public class eventservice {
    
   private Connection cnx;

    public eventservice() {
        cnx = MaConnexion.getInstance().getCnx();

    }
    

    public void addParticipantToEvent(Evenement event, User user) throws SQLException {
        String sql = "INSERT INTO participation (id_event, id_user) VALUES (?, ?)";

        try {
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, event.getId());
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
            System.out.println("Participant ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void ajouter(Evenement e) {
        String sql = "INSERT INTO event(nom_event, lieu_event, date_event,"
                + " prix_event, desc_event,id_theme, id_user,image)VALUES ( ?, ?, ?, ?, ?, ?,?,?)";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            st.setString(1, e.getNomevent());
            st.setString(2, e.getLieu());
            st.setDate(3, Date.valueOf(e.getDate_evenement()));
            st.setFloat(4, e.getPrix());
            st.setString(5, e.getDescription());
            st.setInt(6, e.getIdtheme());
            st.setInt(7, 1);
            st.setString(8, e.getImage());
            //st.executeUpdate();
            st.executeUpdate();
            System.out.println("Evenement ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public ObservableList<Evenement> afficher() {
        ObservableList<Evenement> Evenementt = FXCollections.observableArrayList();

        String sql = "select event.id_event ,event.nom_event ,event.lieu_event, theme.nom_theme, event.date_event ,event.prix_event, event.desc_event, event.image from event INNER JOIN theme where event.id_theme=theme.id_theme ";

        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Evenement p = new Evenement();
                // categorie c=new categorie();
                p.setId(rs.getInt("id_event"));
                p.setNomevent(rs.getString("nom_event"));
                p.setLieu(rs.getString("lieu_event"));

                p.setDate_evenement(rs.getDate("date_event").toLocalDate());
                p.setPrix(rs.getFloat("prix_event"));
                p.setDescription(rs.getString("desc_event"));
                // p.setTheme(new Theme());
                p.setNom(rs.getString("nom_theme"));
                p.setUser(new User());
                p.setImage(rs.getString("image"));
                Evenementt.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Evenementt;

    }

    
    public void supprimer(Evenement e) {
        String sql = "delete from event where id_event = '" + e.getId() + "'";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.executeUpdate(sql);
            System.out.println("Event supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Evenement getEventByNom(String nom_event) throws SQLException {
        String requete = "SELECT * FROM event WHERE nom_event = ?";
        Evenement event = new Evenement();
        try {
            PreparedStatement st = cnx.prepareStatement(requete);
            st.setString(1, nom_event);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                event.setId(rs.getInt("id_event"));
                event.setNomevent(rs.getString("nom_event"));
                event.setLieu(rs.getString("lieu_event"));
                event.setImage(rs.getString("image"));
                event.setPrix(rs.getFloat("prix_event"));
                //event.setNbrparticipant(rs.getInt("nbrparticipant"));
                event.setDescription(rs.getString("desc_event"));
//                event.setUser(rs.getObject("id_user", User));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return event;
    }

    public User getUserById(int id) throws SQLException {
        PreparedStatement statement = cnx.prepareStatement(
                "SELECT * FROM utilisateur WHERE id_user = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id_user"));
            user.setNom(resultSet.getString("nom"));
            user.setPrenom(resultSet.getString("prenom"));
            user.setEmail(resultSet.getString("email"));
            return user;
        } else {
            return null;
        }
    }

    
    public void modifier(Evenement e) {

        String sql = "update event set  desc_event=?,nom_event=?, lieu_event= ?, image=? where id_event='" + e.getId() + "'";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql);
            ste.setString(1, e.getDescription());
            ste.setString(2, e.getNomevent());
            ste.setString(3, e.getLieu());
            //  ste.setInt(4, p.getIdcatt()); 
            //ste.setFloat(4, e.getPrix());
            ste.setString(4, e.getImage());
            // ste.setDate(5, Date.valueOf(e.getDate_evenement()));
            ste.executeUpdate();
            System.out.println("evenement Modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int chercherIdtheme(String nom_theme) throws SQLException {
        int id = 0;
        String requetee = "SELECT id_theme FROM theme where nom_theme='" + nom_theme + "';";
        PreparedStatement pst = cnx.prepareStatement(requetee);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            id = rs.getInt("id_theme");
        }
        return id;
    }

    public List<String> getAll() {
        List<String> list = new ArrayList<String>();
        try {
            String requetee = "SELECT nom_theme FROM theme";
            PreparedStatement pst = cnx.prepareStatement(requetee);
            ResultSet rs = pst.executeQuery();
            System.out.println(rs.toString());

            while (rs.next()) {
                list.add(rs.getString("nom_theme"));
            }

            return list;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

//    public ObservableList<Evenement> chercherEvent(String chaine) {
//        String sql = "SELECT * FROM event WHERE (nom_event LIKE ? or lieu_event LIKE ?  )";
//
//        Connection cnx = Maconnexion.getInstance().getCnx();
//        String ch = "%" + chaine + "%";
//        ObservableList<Evenement> myList = FXCollections.observableArrayList();
//        try {
//
//            Statement ste = cnx.createStatement();
//            // PreparedStatement pst = myCNX.getCnx().prepareStatement(requete6);
//            PreparedStatement stee = cnx.prepareStatement(sql);
//            stee.setString(1, ch);
//            stee.setString(2, ch);
//
//            ResultSet rs = stee.executeQuery();
//            while (rs.next()) {
//                Evenement p = new Evenement();
//                p.setId(rs.getInt(1));
//                p.setDescription(rs.getString(2));
//                p.setNomevent(rs.getString(3));
//                p.setImage(rs.getString(4));
//
//                p.setPrix(rs.getFloat(6));
//                p.setLieu(rs.getString(7));
//                p.setDate_evenement(rs.getDate(8).toLocalDate());
//
//                myList.add(p);
//                System.out.println("titre trouvé! ");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        return myList;
//    }
    public void modifiersstock(int a) {
        Evenement p = new Evenement();
        String sql2 = "UPDATE event set nbrparticipant=nbrparticipant-1 WHERE event.id_event='" + a + "'";
        try {
            PreparedStatement ste = cnx.prepareStatement(sql2);
            ste.setInt(1, p.getNbrparticipant());
            //  ResultSet rs=ste_2.executeQuery(sql2);
            ste.executeUpdate();
            System.out.println("nombre participant modifier");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
    public List<Evenement> SearchByName(String name) {
            List<Evenement> pers = new ArrayList<>();
        try {
            PreparedStatement pre = cnx.prepareStatement("select * from event WHERE nom_event LIKE ?");
            pre.setString(1, "%" + name + "%");
            ResultSet result = pre.executeQuery();
             Statement ste = cnx.createStatement();
        System.out.println(result);
        while (result.next()) {
               Evenement   resultPerson = new Evenement(result.getInt("id_event"), result.getString("nom_event"),result.getString("lieu_event"),result.getFloat("prix_event"), result.getString("desc_event"),result.getString("image"));
            pers.add(resultPerson);
        }
        System.out.println(pers);

    } catch (SQLException ex) {
         System.out.println(ex);
    }
   return pers;

    }

    
    public List<Evenement> sortByDate() {
         List<Evenement> pers = new ArrayList<>();
        try {
           PreparedStatement pre = cnx.prepareStatement("SELECT * FROM event ORDER BY date_event");
           // pre.setString(1, "%" + name + "%");
            ResultSet result = pre.executeQuery();
            Statement ste = cnx.createStatement();
        System.out.println(result);
            while (result.next()) {
                Evenement resultPerson = new Evenement(result.getInt("id_event"), result.getString("nom_event"), result.getString("lieu_event"), result.getFloat("prix_event"), result.getString("desc_event"),result.getDate("date_event").toLocalDate(), result.getString("image"));
                pers.add(resultPerson);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return pers;
    }
}

