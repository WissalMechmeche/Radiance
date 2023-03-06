/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktebipi.gui;

import java.net.URL;
import java.security.Security;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ktebipi.entities.Evenement;
import ktebipi.services.eventService;
import ktebipi.utils.Maconnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.mail.*;
import javax.mail.internet.*; 
import ktebipi.entities.User;

/**
 * FXML Controller class
 *
 * @author ASUS
 *
 *
 */
public class HomeEventFXMLController implements Initializable {

    @FXML
    private TableView<Evenement> listtabevent;

    @FXML
    private TableColumn<Evenement, String> colname;

    @FXML
    private TableColumn<Evenement, Integer> coltheme;

    @FXML
    private TableColumn<Evenement, String> coldesc;

    @FXML
    private TableColumn<Evenement, String> collieu;

    @FXML
    private TableColumn<Evenement, Date> datedebut;

    @FXML
    private TableColumn<Evenement, Float> colprix;

    @FXML
    private TableColumn<Evenement, String> imagecall;

//    @FXML
//    private TableColumn<Evenement, Integer> calid;
    ObservableList<Evenement> platList = FXCollections.observableArrayList();
    Evenement ss = new Evenement();
    Statement ste;
    private Evenement r;
    String query = null;
    Connection connection = null;
    Connection cnx = Maconnexion.getInstance().getCnx();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    eventService es = new eventService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        table();

////        listtabevent.setOnMouseClicked((MouseEvent event) -> {
////            if (event.getClickCount() > 0) {
//////                onEdit();
////            }
//        });
    }

    public void table() {
        ObservableList<Evenement> platlist = es.afficher();

        colname.setCellValueFactory(new PropertyValueFactory<>("Nomevent"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        coltheme.setCellValueFactory(new PropertyValueFactory<>("nom"));
        datedebut.setCellValueFactory(new PropertyValueFactory<>("date_evenement"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        imagecall.setCellValueFactory(new PropertyValueFactory<>("image"));

        TableColumn<Evenement, Void> colBtn = new TableColumn<>("participer");
        Callback<TableColumn<Evenement, Void>, TableCell<Evenement, Void>> cellFactory = new Callback<TableColumn<Evenement, Void>, TableCell<Evenement, Void>>() {
            @Override
            public TableCell<Evenement, Void> call(final TableColumn<Evenement, Void> param) {
                final TableCell<Evenement, Void> cell = new TableCell<Evenement, Void>() {

                    private final Button btn = new Button("participer");

                    {
                        btn.setStyle("-fx-background-color: #4275dc; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 14px;");

                        btn.setOnAction((ActionEvent event) -> {
                            int userid = 1;
                            eventService es = new eventService();
                            Evenement eventClicked = getTableView().getItems().get(getIndex());
                            try {
                                User user = es.getUserById(userid);

                                es.addParticipantToEvent(eventClicked, user);
                                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                                sendMail();
                            } catch (SQLException ex) {
                                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("SUCCES");
                            alert.setHeaderText(null);
                            alert.setContentText("Vous avez participé ! Le paiement se fera lors de l'événement. Merci.");
                            alert.showAndWait();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        listtabevent.getColumns().add(colBtn);
        listtabevent.setItems(platlist);
    }

    public static ObservableList<Evenement> RecupBase() {

        ObservableList<Evenement> list = FXCollections.observableArrayList();

        java.sql.Connection cnx;
        cnx = Maconnexion.getInstance().getCnx();
        String sql = "select *from event";
        try {

            PreparedStatement st = (PreparedStatement) cnx.prepareStatement(sql);

            ResultSet R = st.executeQuery();
            while (R.next()) {
                Evenement r = new Evenement();
                //r.setId(R.getString(1));
                r.setNomevent(R.getString(1));

                // r.setTheme(R.getClass());
                r.setDescription(R.getString(3));

                r.setLieu(R.getString(4));
                r.setDate_evenement(R.getDate(4).toLocalDate());
                r.setImage(R.getString(4));
                r.setPrix(R.getFloat(4));

                list.add(r);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return list;
    }

    public void sendMail() throws SQLException {
        int userid = 1;
        eventService es = new eventService();
        User user = es.getUserById(userid);
        System.out.println(user.getEmail());

        if (user == null) {
            System.out.println("User with ID " + userid + " not found!");
            return;
        } else {
        }

        final String username = "farah.weslati@esprit.tn";
        final String password = "223JFT0698";
        String recipientEmail = user.getEmail();
        String subject = "Payment notification";
        String message = "The payment process has completed successfully.";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            System.out.println("Email notification sent successfully.");
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }

    }
}

//    public void onEdit() {
//
//        java.sql.Connection cnx;
//        cnx = Maconnexion.getInstance().getCnx();
//        String sql = "select *from event";
//
//        PreparedStatement st = null;
//        try {
//            st = (PreparedStatement) cnx.prepareStatement(sql);
//        } catch (SQLException ex) {
//            Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        ResultSet R = null;
//        try {
//            R = st.executeQuery();
//        } catch (SQLException ex) {
//            Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        if (listtabevent.getSelectionModel().getSelectedItem() != null) {
//            Evenement r = listtabevent.getSelectionModel().getSelectedItem();
////            String n = Prixx.getText();
////int p = Integer.valueOf(n);
//            try {
//                r.setNomevent(R.getString(1));
//            } catch (SQLException ex) {
//                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                // r.setTheme(R.getClass());
//                r.setDescription(R.getString(3));
//            } catch (SQLException ex) {
//                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            try {
//                r.setLieu(R.getString(4));
//            } catch (SQLException ex) {
//                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                r.setDate_evenement(R.getDate(4).toLocalDate());
//            } catch (SQLException ex) {
//                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                r.setImage(R.getString(4));
//            } catch (SQLException ex) {
//                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                r.setPrix(R.getFloat(4));
//            } catch (SQLException ex) {
//                Logger.getLogger(HomeEventFXMLController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
//    }
//}
/*
private void load() throws SQLException {
    eventService pp=new eventService();
        Connection Connection = Maconnexion.getInstance().getCnx();
    refplat();
    calid.setCellValueFactory(new PropertyValueFactory<>("id"));
    colname.setCellValueFactory(new PropertyValueFactory<>("Nomevent"));
    coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
    coltheme.setCellValueFactory(new PropertyValueFactory<>("nom"));
    datedebut.setCellValueFactory(new PropertyValueFactory<>("date_evenement"));
    colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    imagecall.setCellValueFactory(new PropertyValueFactory<>("image"));
  // combotheme.setItems(FXCollections.observableArrayList(pp.getAll()));
    } 
 private void refplat() throws SQLException {
     platList.clear();
     query = "select event.id_event ,event.nom_event ,event.lieu_event, theme.nom_theme, event.date_event ,event.prix_event, event.desc_event, event.image from event INNER JOIN theme where event.id_theme=theme.id_theme ";
     preparedStatement = connection.prepareStatement(query);
     resultSet = preparedStatement.executeQuery();
     while (resultSet.next()) {
         platList.add(new Evenement(
                 resultSet.getInt("id_event"),
                 resultSet.getString("nom_event"),
                 resultSet.getString("desc_event"),
                 resultSet.getString("lieu_event"),
                 resultSet.getFloat("prix_event"),
                 resultSet.getDate("date_event").toLocalDate(),
                 //(User) resultSet.getObject("id_user"),
                 resultSet.getString("image"),
                 resultSet.getString("nom_theme")
         ));
         tabevent.setItems(platList);
     }
    }
 */
/**
 * Initializes the controller class.
 */
