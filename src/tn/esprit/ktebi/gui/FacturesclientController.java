/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.entities.LigneFacture;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.AdminService;
import tn.esprit.ktebi.services.ServiceFacture;

/**
 * FXML Controller class
 *
 * @author Pc Anis
 */
public class FacturesclientController implements Initializable {

    @FXML
    private TableView<Facture> table_fac;
    @FXML
    private TableColumn<Facture, String> mode_paiement;
    @FXML
    private TableColumn<Facture, Float> montant_tot;
    @FXML
    private TableColumn<Facture, Timestamp> date_fac;
    @FXML
    private Label txtFac;
    AdminService ad = new AdminService();
    ServiceFacture sf = new ServiceFacture();
    @FXML
    private TextField filterInput;
    private ObservableList<Facture> data;

    /**
     * Initializes the controller class.
     */
    public void showListeFactures() throws SQLException {
        ArrayList<Facture> list = (ArrayList<Facture>) sf.afficherFacturesWithUser();
        System.out.println(list);
        data = FXCollections.observableArrayList(list);

        TableColumn<Facture, String> detailsCol = new TableColumn<>("Details");
        detailsCol.setSortable(false);
        detailsCol.setPrefWidth(80);

        Callback<TableColumn<Facture, String>, TableCell<Facture, String>> cellFactory
                = new Callback<TableColumn<Facture, String>, TableCell<Facture, String>>() {
            @Override
            public TableCell<Facture, String> call(final TableColumn<Facture, String> param) {
                final TableCell<Facture, String> cell = new TableCell<Facture, String>() {
                    final Button btn = new Button("Details");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setStyle("-fx-background-color: #4275dc; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 14px;");
                            btn.setOnAction(event -> {
                                Facture facture = getTableView().getItems().get(getIndex());
                                try {
                                    showLignesFacturePopup(facture);
                                } catch (SQLException ex) {
                                    Logger.getLogger(FacturesclientController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        detailsCol.setCellFactory(cellFactory);

        mode_paiement.setCellValueFactory(new PropertyValueFactory<Facture, String>("mode_paiement"));
        montant_tot.setCellValueFactory(new PropertyValueFactory<Facture, Float>("montant_totale"));
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

        table_fac.getColumns().add(detailsCol);
        table_fac.setItems(data);
    }

    //popupLigneFactures
    public void showLignesFacturePopup(Facture f) throws SQLException {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL.APPLICATION_MODAL);
        popup.setTitle("Lignes de factures");
        popup.setMinWidth(350);
        popup.setMinHeight(500);

        TableView<LigneFacture> table = new TableView<>();
        TableColumn<LigneFacture, String> livreCol = new TableColumn<>("Livre");

        livreCol.setCellValueFactory(cellData -> {
            LigneFacture ligneFacture = cellData.getValue();
            int livreId = ligneFacture.getId_livre().getId();
            try {
                Livre livre = sf.getLivreById(livreId);
                String libelle = livre.getLibelle();
                return new SimpleStringProperty(libelle);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });

        TableColumn<LigneFacture, Integer> qteCol = new TableColumn<>("Quantité");
        qteCol.setCellValueFactory(new PropertyValueFactory<>("qte"));
        TableColumn<LigneFacture, Float> mntCol = new TableColumn<>("Montant");
        mntCol.setCellValueFactory(new PropertyValueFactory<>("mnt"));

        table.getColumns().addAll(livreCol, qteCol, mntCol);
        table.setItems(FXCollections.observableArrayList(sf.afficherLignesFacturesByFactureId(f.getId())));

        Scene scene = new Scene(table);
        popup.setScene(scene);
        popup.showAndWait();
    }

    public void filterFactureList(String oldValue, String newValue) {
        ObservableList<Facture> filteredList = FXCollections.observableArrayList();
        if (this.filterInput != null && newValue.length() >= oldValue.length() && newValue != null) {
            newValue = newValue.toUpperCase();
            Iterator var4 = this.table_fac.getItems().iterator();

            while (true) {

                Facture factures;
                String filterModePaiement;

                do {
                    if (!var4.hasNext()) {
                        this.table_fac.setItems(filteredList);
                        return;
                    }

                    factures = (Facture) var4.next();
                    filterModePaiement = factures.getMode_paiement();

                } while (!filterModePaiement.toUpperCase().contains(newValue));

                filteredList.add(factures);
            }
        } else {
            this.table_fac.setItems(data);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showListeFactures();
            this.filterInput.textProperty().addListener(new ChangeListener() {
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    FacturesclientController.this.filterFactureList((String) oldValue, (String) newValue);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(FacturesclientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}