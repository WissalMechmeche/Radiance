/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import com.jfoenix.controls.JFXComboBox;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import tn.esprit.ktebi.entities.Evenement;
import tn.esprit.ktebi.services.Scontrol;
import tn.esprit.ktebi.services.eventService;
import tn.esprit.ktebi.utils.MaConnexion;
import tn.esprit.ktebi.entities.User;
import javax.mail.*;
import javax.mail.internet.*;

import org.controlsfx.control.Notifications;
import tn.esprit.ktebi.entities.Theme;
import tn.esprit.ktebi.services.UserService;

/**
 * FXML Controller class
 *
 * @author MSI
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
    private TextField recherche;

    @FXML
    private Button btnRechercher;
    @FXML
    private Button tri;

//    @FXML
//    private TextField rechercherEvent;
    private ObservableList<Evenement> data = FXCollections.observableArrayList();
    ObservableList<Evenement> platList = FXCollections.observableArrayList();

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
        try {
            eventService pp = new eventService();
            java.sql.Connection cnx = MaConnexion.getInstance().getCnx();
            calid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colname.setCellValueFactory(new PropertyValueFactory<>("Nomevent"));
            coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            coltheme.setCellValueFactory(new PropertyValueFactory<>("theme.nom"));
            datedebut.setCellValueFactory(new PropertyValueFactory<>("date_evenement"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            combotheme.setItems(FXCollections.observableArrayList(pp.getAll()));
            tabevent.setItems(platList);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    private void SelectItems(MouseEvent event) {
        Evenement cattt = tabevent.getSelectionModel().getSelectedItem();

        txtNomEvent.setText(tabevent.getSelectionModel().getSelectedItem().getNomevent());
        txtLieu.setText(tabevent.getSelectionModel().getSelectedItem().getLieu());
        txtPrix.setText(Float.toString(tabevent.getSelectionModel().getSelectedItem().getPrix()));
        java.sql.Date sqlDate = (java.sql.Date) tabevent.getSelectionModel().getSelectedItem().getDate_evenement();
        LocalDate localDate = sqlDate.toLocalDate();
        txtDate.setValue(localDate);

        imagefild.setText(tabevent.getSelectionModel().getSelectedItem().getImage());

        combotheme.setValue(tabevent.getSelectionModel().getSelectedItem().getTheme().getNom());

        txtDescription.setText(tabevent.getSelectionModel().getSelectedItem().getDescription());
        String path = cattt.getImage();
        File file = new File(path);
        Image img = new Image(file.toURI().toString());
        imageview.setImage(img);

    }

    @FXML
    public void ResetEvenet(ActionEvent event) {
        txtDescription.clear();
        txtDate.setValue(null);
        combotheme.setValue(null);

        imagefild.clear();
        txtLieu.clear();
        txtNomEvent.clear();
        txtPrix.clear();

    }

    @FXML
    private void deletehandler(ActionEvent event) throws SQLException, NullPointerException {
        if (!tabevent.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sur de vouloir supprimer un event " + tabevent.getSelectionModel().getSelectedItem().getNomevent() + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                eventService r = new eventService();
                r.supprimer(tabevent.getSelectionModel().getSelectedItem());
                ResetEvenet(event);
                tabevent.getItems().remove(tabevent.getSelectionModel().getSelectedItem());

                Notifications notificationBuild = Notifications.create()
                        .title("Traitement réclamation ")
                        .text("l'évenement a été supprimé avec succes")
                        .graphic(null)
                        .position(Pos.CENTER)
                        .onAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println("click here");
                            }

                        });
                notificationBuild.show();

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("il faut séléctionner une ligne");
            alert.showAndWait();
        }
    }

    @FXML
    void modifevent(ActionEvent event) throws SQLException {

        String description = txtDescription.getText();
        String nom = txtNomEvent.getText();
        String lieu = txtLieu.getText();
        Float prix = Float.parseFloat(txtPrix.getText());
        String image = imagefild.getText();
        String theme = combotheme.getValue();
        LocalDate date_event = txtDate.getValue();

        Evenement e = new Evenement();

        eventService es = new eventService();
        e.setDescription(description);
        e.setNomevent(nom);
        e.setPrix(prix);
        e.setImage(image);
        e.setLieu(lieu);

        List<Theme> listT = es.getAllThemes();
        Theme a = null;
        
        for (Theme th : listT) {
            String temp = th.getNom();
            if (temp == theme) {
                a = new Theme(th.getIdtheme(), th.getNom());
                System.out.println(a);
            }

        }
        e.setUser(new User(1));
        e.setDate_evenement(new java.sql.Date(Date.valueOf(date_event).getTime()));
        e.setTheme(new Theme(2,"faza"));

        System.out.println(e);
        es.modifier(e);

        refplat();

    }

    private void refplat() {
        try {
            java.sql.Connection cnx;
            cnx = MaConnexion.getInstance().getCnx();

            String query = "select event.id_event ,event.nom_event ,event.lieu_event, theme.id_theme, theme.nom_theme ,"
                    + " event.date_event ,event.prix_event, event.desc_event, event.image from event "
                    + "INNER JOIN theme where event.id_theme=theme.id_theme ";

            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Evenement p = new Evenement();
                // categorie c=new categorie();
                p.setId(rs.getInt("id_event"));
                p.setNomevent(rs.getString("nom_event"));
                p.setLieu(rs.getString("lieu_event"));

                p.setDate_evenement(rs.getDate("date_event"));
                p.setPrix(rs.getFloat("prix_event"));
                p.setDescription(rs.getString("desc_event"));
                // p.setTheme(new Theme());

                Theme t = new Theme();
                int id_theme = rs.getInt("id_theme");
                t.setIdtheme(id_theme);
                t.setNom(rs.getString("nom_theme"));
                p.setTheme(t);
                System.out.println(t);
                p.setUser(new User(1));
                p.setImage(rs.getString("image"));
                platList.add(p);
            }
            tabevent.setItems(platList);
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
        String DBPath = "C:\\Users\\melek\\OneDrive\\Documents\\NetBeansProjects\\Ktebi\\src\\tn\\esprit\\ktebi\\ressources\\images" + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path = file.getAbsolutePath();
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
        java.sql.Connection cnx;

        cnx = MaConnexion.getInstance().getCnx();
        String Nomevent = txtNomEvent.getText();
        String description = txtDescription.getText();

        String id_theme = combotheme.getValue();
        eventService rec = new eventService();
        int idth = rec.chercherIdtheme(id_theme);
        Theme t = new Theme();
        t.setIdtheme(idth);

        String lieu = txtLieu.getText();
        float prix = Float.parseFloat(txtPrix.getText());
        LocalDate date_evenement = txtDate.getValue();

        User u = new User();
        Scontrol sc = new Scontrol();

        Evenement re = new Evenement(Nomevent, description, lieu, prix, new java.sql.Date(Date.valueOf(date_evenement).getTime()), u, imagefild.getText(), t);

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
            tabevent.getItems().add(re);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("event ajouter");
            alert.showAndWait();
        }
    }

    private void populateTable(ObservableList<Evenement> branlist) {
        tabevent.setItems(branlist);

    }

    @FXML
    void eventrecherche(KeyEvent event) {
        eventService bs = new eventService();
        Evenement b = new Evenement();
        ObservableList<Evenement> filter = bs.chercherEvent(recherche.getText());
        populateTable(filter);
    }

    @FXML
    void rechercherbar(KeyEvent event) {
        eventService bs = new eventService();
        Evenement b = new Evenement();
        ObservableList<Evenement> filter = bs.chercherEvent(recherche.getText());
        populateTable(filter);
    }

    @FXML
    private void rechercher(ActionEvent event) {
        eventService met = new eventService();
        //ServiceUser sca = new ServiceUser();
        System.out.println("/////////////recherche//////////");
        System.out.println(recherche.getText());
        ObservableList<Evenement> data = FXCollections.observableArrayList(met.SearchByName(recherche.getText()));
        System.out.println(data);
        colname.setCellValueFactory(new PropertyValueFactory<>("nomevent"));
        coldesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        tabevent.setItems(data);
    }
//

    @FXML
    void tri(ActionEvent event) {
        eventService met = new eventService();
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

    /*
    @FXML
    private void ConvertToexcel(ActionEvent event) {
        try {
            String filename = "src/tn/esprit/ktebipi/gui/data.xls";
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Nom_Event");
            rowhead.createCell((short) 4).setCellValue("Description");
            rowhead.createCell((short) 1).setCellValue("Lieu");
            rowhead.createCell((short) 2).setCellValue("Date_deb");
            rowhead.createCell((short) 3).setCellValue("Prix");
            rowhead.createCell((short) 5).setCellValue("Nom theme");
            rowhead.createCell((short) 6).setCellValue("Prenom client");
            rowhead.createCell((short) 7).setCellValue("Prenom client");

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("select * from event");
            int i = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow((short) i);

                row.createCell((short) 0).setCellValue(rs.getString("nom_event"));
                row.createCell((short) 1).setCellValue(rs.getString("lieu_evenet"));
                row.createCell((short) 2).setCellValue(rs.getDate("date_event").toLocalDate());
                row.createCell((short) 3).setCellValue(Float.toString(rs.getFloat("prix_event")));
                row.createCell((short) 4).setCellValue(rs.getString("desc_event"));
                int id_theme = rs.getInt("id_theme");
                Evenement e = new Evenement();
                e.setId(id_theme);
                row.createCell((short) 5).setCellValue(e.getNom());

                int id_user = rs.getInt("id_user");
                User u = new User();
                u.setId(id_user);
                row.createCell((short) 6).setCellValue(u.getNom());
                row.createCell((short) 7).setCellValue(u.getPrenom());

                i++;
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");
            File file = new File(filename);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }
    }*/
}
