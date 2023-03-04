/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import java.util.ArrayList;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import tn.esprit.ktebi.entities.Facture;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.ktebi.services.AdminService;
import tn.esprit.ktebi.services.ServiceFacture;

/**
 *
 * @author Pc Anis
 */
public class FacturesAdminControlleur implements Initializable {

    AdminService ad = new AdminService();
    ServiceFacture sf = new ServiceFacture();

    @FXML
    private TableView<Facture> table_fac;
    @FXML
    private TableColumn<Facture, String> mode_paiement;
    @FXML
    private TableColumn<Facture, Float> montant_tot;
    @FXML
    private TableColumn<Facture, String> prenom;
    @FXML
    private TableColumn<Facture, String> nom;
    private ObservableList<Facture> data = FXCollections.observableArrayList();
    @FXML
    private Label txtFac;
    @FXML
    private Button btnSupp;
    @FXML
    private TableColumn<Facture, Timestamp> date_fac;

    @FXML
    private void handleLabelDash(MouseEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Admin-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void showListeFactures() throws SQLException {
        ArrayList<Facture> list = (ArrayList<Facture>) sf.afficherFacturesWithUser();
        System.out.println(list);
        ObservableList<Facture> data = FXCollections.observableArrayList(list);
        mode_paiement.setCellValueFactory(new PropertyValueFactory<Facture, String>("mode_paiement"));
        montant_tot.setCellValueFactory(new PropertyValueFactory<Facture, Float>("montant_totale"));
        nom.setCellValueFactory(new PropertyValueFactory<Facture, String>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<Facture, String>("prenom"));
        date_fac.setCellValueFactory(new PropertyValueFactory<Facture, Timestamp>("date_fac"));

        date_fac.setCellFactory(column -> {
            return new TableCell<Facture, Timestamp>() {
                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        LocalDateTime localDateTime = item.toLocalDateTime();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        setText(localDateTime.format(formatter));
                    }
                }
            };
        });


        txtFac.setText("Nombre Factures: " + ad.getTotalFactures());
        date_fac.setPrefWidth(150); // Remplacez 150 par la largeur souhaitée

        table_fac.setItems(data);

    }

    @FXML
    private void deleteFacture(ActionEvent event) throws SQLException {
        try {

            Facture selectedFacture = table_fac.getSelectionModel().getSelectedItem();
            System.err.println(selectedFacture);

            if (selectedFacture == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucun livre sélectionné");
                alert.setContentText("Veuillez sélectionner un livre dans la table !");
                alert.showAndWait();
                return;
            }

            sf.supprimerFacture(selectedFacture);
            showListeFactures();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Facture supprimée!");
            alert.setHeaderText(null);
            alert.setContentText("La facture a été supprimé!");
            alert.showAndWait();
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune Facture sélectionné");
            alert.setContentText("Veuillez sélectionner une facture dans la table !");
            alert.showAndWait();

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showListeFactures();
        } catch (SQLException ex) {
            Logger.getLogger(FacturesAdminControlleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}