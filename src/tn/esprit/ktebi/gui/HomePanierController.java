/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;

/**
 *
 * @author Pc Anis
 */
public class HomePanierController implements Initializable {

    @FXML
    private TextField filterInput;

    @FXML

    private Button panierBtn;
    private PauseTransition pause = null;

    ServiceLignePanier lp = new ServiceLignePanier();

    ObservableList<Livre> livre;
    @FXML
    private TableView<Livre> table_res;
    @FXML
    private TableColumn<Livre, String> image;
    @FXML
    private TableColumn<Livre, String> categorie;
    @FXML
    private TableColumn<Livre, Float> prix;
    @FXML
    private TableColumn<Livre, String> libelle;
    private ObservableList<Livre> data = FXCollections.observableArrayList();

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    public void closeApp() {
        //App.getWindow().close();
    }

    public void filterLivreList(String oldValue, String newValue) {
        ObservableList<Livre> filteredList = FXCollections.observableArrayList();
        if (this.filterInput != null && newValue.length() >= oldValue.length() && newValue != null) {
            newValue = newValue.toUpperCase();
            Iterator var4 = this.table_res.getItems().iterator();

            while (true) {
                Livre livres;
                String filterLibelle;
                String filterCategorie;
                String filterPrix;

                do {
                    if (!var4.hasNext()) {
                        this.table_res.setItems(filteredList);
                        return;
                    }

                    livres = (Livre) var4.next();
                    filterLibelle = livres.getLibelle();
                    filterCategorie = livres.getCategorie();
                    filterPrix = String.valueOf(livres.getPrix());
                } while (!filterLibelle.toUpperCase().contains(newValue) && !filterCategorie.toUpperCase().contains(newValue) && !filterPrix.toUpperCase().contains(newValue));

                filteredList.add(livres);
            }
        } else {
            this.table_res.setItems(data);
        }
    }

    public void showLivres() {
        try {
            ObservableList<Livre> data = lp.listelivres();

            libelle.setCellValueFactory(new PropertyValueFactory<Livre, String>("libelle"));
            image.setCellValueFactory(new PropertyValueFactory<Livre, String>("image"));

            image.setCellFactory(column -> {
                return new TableCell<Livre, String>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            try {
                                Image img = new Image(item);
                                imageView.setImage(img);
                                imageView.setFitHeight(80);
                                imageView.setPreserveRatio(true);
                                setGraphic(imageView);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
            });
            categorie.setCellValueFactory(new PropertyValueFactory<Livre, String>("categorie"));
            prix.setCellValueFactory(new PropertyValueFactory<Livre, Float>("prix"));

            table_res.setItems(data);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ajoutAuPanier() {
        TableColumn<Livre, Void> colBtn = new TableColumn<>("Panier");
        Callback<TableColumn<Livre, Void>, TableCell<Livre, Void>> cellFactory = new Callback<TableColumn<Livre, Void>, TableCell<Livre, Void>>() {
            @Override
            public TableCell<Livre, Void> call(final TableColumn<Livre, Void> param) {
                final TableCell<Livre, Void> cell = new TableCell<Livre, Void>() {

                    private final Button btn = new Button("Ajouter au panier");

                    {
                        btn.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 14px;");

                        btn.setOnAction((ActionEvent event) -> {

                            ServicePanier sp = new ServicePanier();
                            Livre livre = (Livre) getTableRow().getItem();
                            System.out.println("Ligne sélectionnée : " + getIndex());
                            try {
                                livre = sp.getLivreByLibelle(livre.getLibelle());

                                if (livre != null) {

                                    try {
                                        int userId = 1;
                                        User user = new User();
                                        user.setId(userId);
                                        ServicePanier servicePanier = new ServicePanier();
                                        Panier panier = servicePanier.getPanierByUser(userId);
                                        if (panier == null) {

                                            panier = new Panier(0, 0, user);
                                            servicePanier.ajouterPanier(panier);
                                            panier = servicePanier.getPanierByUser(userId);

                                        } else {
                                            // Sinon, mettre à jour l'utilisateur du panier (au cas où il a été créé pour un autre utilisateur)
                                            panier.setUser(user);
                                            servicePanier.modifierPanier(panier);
                                        }

                                        // Vérifier si le livre sélectionné existe déjà dans le panier
                                        LignePanier lignePanierExistante = lp.getLignePanierByLivreAndPanier(livre, panier);

                                        if (lignePanierExistante != null) {
                                            // Si la lignePanier existe déjà, augmenter la quantité
                                            lignePanierExistante.setQuantite(lignePanierExistante.getQuantite() + 1);
                                            lp.modifierLignePanier(lignePanierExistante);
                                            System.out.println("Quantité du livre dans le panier augmentée avec succès !");
                                        } else {
                                            // Sinon, créer une nouvelle lignePanier pour le livre sélectionné
                                            LignePanier lignePanier = new LignePanier();
                                            lignePanier.setLivre(livre);
                                            lignePanier.setPanier(panier); // panier est toujours null ici si aucun panier n'a été trouvé pour l'utilisateur
                                            lignePanier.setPanier(panier);
                                            lignePanier.setQuantite(1);
                                            lp.ajouterLignePanier(lignePanier);
                                            System.out.println("Livre ajouté au panier avec succès !");
                                        }

                                        // Afficher un message de succès
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Ajout au panier");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Le livre a été ajouté au panier avec succès!");
                                        alert.showAndWait();

                                    } catch (SQLException ex) {
                                        System.out.println(ex.getMessage());
                                        // Afficher un message d'erreur
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Erreur");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Une erreur est survenue lors de l'ajout du livre au panier!");
                                        alert.showAndWait();
                                    }
                                } else {
                                    // Si aucun livre n'a été sélectionné, afficher un message d'erreur
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Sélectionnez un livre");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Veuillez sélectionner un livre à ajouter au panier!");
                                    alert.showAndWait();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(HomePanierController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
        table_res.getColumns().add(colBtn);
    }

    private Parent fxml;

    @FXML
    public void showPanierPage(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("cart-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void showHome(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeCart.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLivres();
        ajoutAuPanier();

        this.filterInput.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HomePanierController.this.filterLivreList((String) oldValue, (String) newValue);
            }
        });

    }

    @FXML
    private void showFacture(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Facture-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}
