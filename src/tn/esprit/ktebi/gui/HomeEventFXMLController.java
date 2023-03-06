/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

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
import tn.esprit.ktebi.entities.Evenement;
import tn.esprit.ktebi.services.eventService;
import tn.esprit.ktebi.utils.MaConnexion;

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
import tn.esprit.ktebi.entities.User;

/**
 * FXML Controller class
 *
 * @author Pc Anis
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
        eventService es = new eventService();
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
        String sql = "select * from event";
        try {
            cnx = MaConnexion.getInstance().getCnx();

            PreparedStatement st = (PreparedStatement) cnx.prepareStatement(sql);

            ResultSet R = st.executeQuery();
            while (R.next()) {
                Evenement r = new Evenement();
                //r.setId(R.getString(1));
                r.setNomevent(R.getString(1));

                // r.setTheme(R.getClass());
                r.setDescription(R.getString(3));

                r.setLieu(R.getString(4));
                r.setDate_evenement(R.getDate(4));
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
