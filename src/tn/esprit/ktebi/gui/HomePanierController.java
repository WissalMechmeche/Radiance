/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class HomePanierController implements Initializable {

    private Connection cnx;

    public HomePanierController() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    @FXML
    private BorderPane contentPane;

    @FXML

    private Button panierBtn;

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
    public ObservableList<Livre> data = FXCollections.observableArrayList();

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    ServiceLignePanier rcd = new ServiceLignePanier();

    public void closeApp() {
        App.getWindow().close();
    }

    public void showLivres() {
        try {
            String requete = "SELECT * FROM Livre";
            PreparedStatement st = cnx.prepareStatement(requete);
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                String image = rs.getString(10); 
                Image img = new Image(image);

                data.add(new Livre(rs.getString(2), image, rs.getString(6), rs.getFloat(9)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

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

    }

    public void ajoutAuPanier() {
        TableColumn<Livre, Void> colBtn = new TableColumn<>("Panier");
        Callback<TableColumn<Livre, Void>, TableCell<Livre, Void>> cellFactory = new Callback<TableColumn<Livre, Void>, TableCell<Livre, Void>>() {
            @Override
            public TableCell<Livre, Void> call(final TableColumn<Livre, Void> param) {
                final TableCell<Livre, Void> cell = new TableCell<Livre, Void>() {

                    private final Button btn = new Button("Ajouter au panier");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            ServicePanier sp = new ServicePanier();
                            Livre livre = (Livre) getTableRow().getItem();
                            System.out.println("Ligne sélectionnée : " + getIndex());
                            try {
                                livre = sp.getLivreByLibelle(livre.getLibelle());

                                if (livre != null) {

                                    try {
                                        int userId = 2;
                                        ServicePanier servicePanier = new ServicePanier();
                                        Panier panier = servicePanier.getPanierByUser(userId);
                                        if (panier == null) {
                                            panier = new Panier(0, 0, userId, 0);
                                            servicePanier.ajouterPanier(panier);
                                        }

                                        // Vérifier si le livre sélectionné existe déjà dans le panier
                                        ServiceLignePanier serviceLigne = new ServiceLignePanier();
                                        LignePanier lignePanierExistante = serviceLigne.getLignePanierByLivreAndPanier(livre.getId(), panier.getId());

                                        if (lignePanierExistante != null) {
                                            // Si la lignePanier existe déjà, augmenter la quantité
                                            lignePanierExistante.setQuantite(lignePanierExistante.getQuantite() + 1);
                                            serviceLigne.modifierLignePanier(lignePanierExistante);
                                            System.out.println("Quantité du livre dans le panier augmentée avec succès !");
                                        } else {
                                            // Sinon, créer une nouvelle lignePanier pour le livre sélectionné
                                            LignePanier lignePanier = new LignePanier();
                                            lignePanier.setLivre(livre.getId());
                                            lignePanier.setPanier(panier.getId());
                                            lignePanier.setQuantite(1);
                                            serviceLigne.ajouterLignePanier(lignePanier);
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
    private void showPanierPage(ActionEvent event) throws IOException {
        URL url = new File("C:\\Users\\Pc Anis\\Documents\\NetBeansProjects\\Radiance-master\\src\\tn\\esprit\\ktebi\\gui\\cart-ui.fxml").toURI().toURL();
        Parent panierPage = FXMLLoader.load(url);

        Scene panierPageScene = new Scene(panierPage);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(panierPageScene);
        appStage.show();
    }
    
      @FXML
    private void showHome(ActionEvent event) throws IOException {
        URL url = new File("C:\\Users\\Pc Anis\\Documents\\NetBeansProjects\\Radiance-master\\src\\tn\\esprit\\ktebi\\gui\\homeCart.fxml").toURI().toURL();
        Parent panierPage = FXMLLoader.load(url);

        Scene panierPageScene = new Scene(panierPage);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(panierPageScene);
        appStage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showLivres();
        ajoutAuPanier();

    }

}
