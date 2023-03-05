/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktebipi.gui;

import com.jfoenix.controls.JFXComboBox;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.console;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import ktebipi.entities.Evenement;
import ktebipi.services.Scontrol;
import ktebipi.services.eventService;
import ktebipi.utils.Maconnexion;
import tn.esprit.ktebi.entities.Theme;
import tn.esprit.ktebi.entities.User;
import javax.mail.*;
import javax.mail.internet.*;
import ktebipi.services.Metier;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class EventController implements Initializable {

    @FXML
    private TextField txtLieu;

    @FXML
    private TextField txtPrix;

    @FXML
    private DatePicker txtDate;

    @FXML
    private TextField txtNomEvent;

    @FXML
    private TextArea txtDescription;
    @FXML
    private Button ajouter;

     @FXML
    private JFXComboBox<String> combotheme;
    @FXML
    private TextField imagefild;


    @FXML
    private TableView<Evenement> tabevent;

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

    @FXML
    private TableColumn<Evenement, Integer> calid;

    @FXML
    private Button btnsupp;

    @FXML
    private ImageView imageview;

    @FXML
    private Button btnupload;
@FXML
    private TextField recherche;

    @FXML
    private Button btnRechercher;
       @FXML
    private Button tri;


    
//    @FXML
//    private TextField rechercherEvent;

    private ObservableList<Evenement> data = FXCollections.observableArrayList();

    eventService se = new eventService();

    ObservableList<Evenement> platList = FXCollections.observableArrayList();
    Evenement ss = new Evenement();
    Statement ste;
    private Evenement r;
    String query = null;
    Connection connection = null;
    Connection cnx = Maconnexion.getInstance().getCnx();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private Button bntmodif;
    @FXML
    private Button btnreset;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
      load();
      refplat();

    }

   
 private void load() {
    eventService pp=new eventService();
    connection= Maconnexion.getInstance().getCnx();
    refplat();
    calid.setCellValueFactory(new PropertyValueFactory<>("id"));
    colname.setCellValueFactory(new PropertyValueFactory<>("Nomevent"));
    coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
    coltheme.setCellValueFactory(new PropertyValueFactory<>("nom"));
    datedebut.setCellValueFactory(new PropertyValueFactory<>("date_evenement"));
    colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    imagecall.setCellValueFactory(new PropertyValueFactory<>("image"));
    combotheme.setItems(FXCollections.observableArrayList(pp.getAll()));
    }   
    
    @FXML
    private void SelectItems(MouseEvent event) {
        Evenement cattt = tabevent.getSelectionModel().getSelectedItem();
//        nom_livre.clear();
//
//        cat.clear();
//        prixx.clear();
//        quantitee.clear();
//        nom_livre.setDisable(true);
//        cat.setDisable(true);
//        prixx.setDisable(true);
//        quantitee.setDisable(false);
//        nom_livre.appendText(table_panier.getSelectionModel().getSelectedItem().getLibelle());
//        cat.appendText(table_panier.getSelectionModel().getSelectedItem().getCategorie());
//        prixx.appendText(Float.toString(table_panier.getSelectionModel().getSelectedItem().getPrix()));
//        quantitee.appendText(Integer.toString(table_panier.getSelectionModel().getSelectedItem().getUser()));
        txtNomEvent.appendText(tabevent.getSelectionModel().getSelectedItem().getNomevent());
        txtLieu.appendText(tabevent.getSelectionModel().getSelectedItem().getLieu());
        txtPrix.appendText(Float.toString(tabevent.getSelectionModel().getSelectedItem().getPrix()));
//        txtDate.appendText(LocalDate.parse(tabevent.getSelectionModel().getSelectedItem().getDate_evenement()));
        txtDescription.appendText(tabevent.getSelectionModel().getSelectedItem().getDescription());
          String path = cattt.getImage();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview.setImage(img);

    }

    public void sendMail() throws SQLException {
        int userid = 3;
        eventService es = new eventService();
        User user = es.getUserById(userid);
        System.out.println(user.getEmail());

        if (user == null) {
            System.out.println("User with ID " + userid + " not found!");
            return;
        } else {
        }

        final String username = "farah.weslati@esprit.tn";
        final String password = "fariiiiihaaa (mdp mail)";
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

    @FXML
    public void ResetEvenet(ActionEvent event) {
        txtDescription.clear();
//        txtDate.clear();
        txtLieu.clear();
        txtNomEvent.clear();
        txtPrix.clear();

    }

    @FXML
    private void deletehandler(ActionEvent event) throws SQLException, NullPointerException {
        if (!tabevent.getSelectionModel().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sur de vouloir supprimer plat " + tabevent.getSelectionModel().getSelectedItem().getNomevent()+ " ?", ButtonType.YES, ButtonType.NO);
alert.showAndWait();

if (alert.getResult() == ButtonType.YES) {
    eventService r=new eventService();
    r.supprimer(tabevent.getSelectionModel().getSelectedItem());
     Notifications notificationBuild = Notifications.create()
                                      .title("Traitement réclamation ")
                                      .text("la réclamation a été supprimé avec succes")
                                      .graphic(null)
                                      .position(Pos.CENTER)
                                      .onAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent event) {
                                      System.out.println("click here");
                                  }
                                  
                              });
                              notificationBuild.show(); 
    refplat();
}
 
        }
         else{
              Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("il faut séléctionner une ligne");
            alert.showAndWait();}
    }
   @FXML
    void modifevent(ActionEvent event) {
 Evenement cat=new Evenement();
   eventService pl = new eventService();
   cat=tabevent.getSelectionModel().getSelectedItem();
   cat.setDescription(txtDescription.getText());
   cat.setNom(txtNomEvent.getText());
   cat.setImage(imagefild.getText());
  
//   float prix=Float.valueOf(txtPrix.getText());
  // cat.setPrix(prix);
 cat.setLieu(txtLieu.getText());
 //cat.setDate_evenement(txtDate.getValue());
   pl.modifier(cat);
   load(); 
refplat(); 
    }

    private void refplat() {
        try {
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
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
 
      @FXML
    void imageupload(ActionEvent event) throws FileNotFoundException, IOException {
   Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\\\xampp\\\\htdocs\\\\piKtebi\\\\"+  x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageview.setImage(img);
           /* File File1 = new File(DBPath);
            Image img = new Image(File1.getAbsolutePath());
            image_event.setImage(img);*/
            imagefild.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();
            
        } else {
            System.out.println("error");

        }
    }
    
      @FXML
    void ajouterrr(ActionEvent event) throws SQLException {
  connection = Maconnexion.getInstance().getCnx();
        String Nomevent = txtNomEvent.getText();
        String description = txtDescription.getText();
        
          String id_theme = combotheme.getValue();
           eventService rec = new eventService();
        int idth = rec.chercherIdtheme(id_theme);
        String lieu = txtLieu.getText();
        float prix = Float.parseFloat(txtPrix.getText());
       LocalDate date_evenement = txtDate.getValue();
            User u=new User();
        Scontrol sc = new Scontrol();

       Evenement re = new Evenement(Nomevent, description, lieu, prix,date_evenement,u, imagefild.getText(), idth);

        if (Nomevent.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setContentText("champs vides");
            alert.showAndWait();
        } else if (!sc.isNumeric(txtPrix.getText())) {
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("quantité doit étre un nombre");
            alert.showAndWait();
       } else {
            rec.ajouter(re);
            refplat();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("event ajouter");
           alert.showAndWait();
        }
    }
//  private void populateTable(ObservableList<Evenement> branlist){
//       tabevent.setItems(branlist);
//   
//    }
//      @FXML
//    void eventrecherche(KeyEvent event) {
// eventService bs=new eventService(); 
//        Evenement b= new Evenement();
//        ObservableList<Evenement>filter= bs.chercherEvent(rechercherEvent.getText());
//      populateTable(filter);
//    }
////@FXML
//void rechercherbar(KeyEvent event){
//      eventService bs=new eventService(); 
//        Evenement b= new Evenement();
//        ObservableList<Evenement>filter= bs.chercherEvent(rechercher.getText());
//        populateTable(filter); 
//}

    @FXML
    private void rechercher(ActionEvent event) {
         Metier met = new Metier();
        //ServiceUser sca = new ServiceUser();
        System.out.println("/////////////recherche//////////");
        System.out.println(recherche.getText());
        ObservableList<Evenement> data = FXCollections.observableArrayList(met.SearchByName(recherche.getText()));
        System.out.println(data);
        colname.setCellValueFactory(new PropertyValueFactory<>("nomevent"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        imagecall.setCellValueFactory(new PropertyValueFactory<>("image"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        tabevent.setItems(data);
    }

    @FXML
    private void rechercherbar(KeyEvent event) {
    }
    @FXML
    void tri(ActionEvent event) {
             Metier met = new Metier();
        //ServiceUser sca = new ServiceUser();
        System.out.println("/////////////recherche//////////");
        System.out.println(tri.getText());
//        List<Evenement> data = Metier.sortByDate();
        ObservableList<Evenement> data = FXCollections.observableArrayList(met.sortByDate());
        System.out.println(data);
        colname.setCellValueFactory(new PropertyValueFactory<>("nomevent"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        imagecall.setCellValueFactory(new PropertyValueFactory<>("image"));
        datedebut.setCellValueFactory(new PropertyValueFactory<>("date_evenement"));
         colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        tabevent.setItems(data);

    }
    
}

