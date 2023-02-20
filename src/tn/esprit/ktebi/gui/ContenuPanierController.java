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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class ContenuPanierController implements Initializable {

    private Connection cnx;

    public ContenuPanierController() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    ObservableList<Livre> livre;
    @FXML
    private TableView<Livre> table_panier;
    @FXML
    private TableColumn<Livre, String> image;
    @FXML
    private TableColumn<Livre, String> categorie;
    @FXML
    private TableColumn<Livre, Float> prix;
    @FXML
    private TableColumn<Livre, String> libelle;

    @FXML
    private TableColumn<Livre, String> quantite;
    public ObservableList<Livre> data = FXCollections.observableArrayList();

    public void showPanier() {
        try {
            // Récupérer le panier de l'utilisateur
            String panierQuery = "SELECT * FROM Panier WHERE id_user=?";
            PreparedStatement panierStmt = cnx.prepareStatement(panierQuery);
            int userId = 2; // utilisateur statique
            panierStmt.setInt(1, userId);

            ResultSet panierRs = panierStmt.executeQuery();

            // Si l'utilisateur n'a pas de panier, afficher un message et sortir de la fonction
            if (!panierRs.next()) {
                System.out.println("Le panier de l'utilisateur est vide.");
                return;
            }

            // Récupérer les lignes de panier pour le panier de l'utilisateur
            int panierId = panierRs.getInt("id_panier");
            String lignePanierQuery = "SELECT * FROM ligne_panier WHERE id_panier=?";
            PreparedStatement lignePanierStmt = cnx.prepareStatement(lignePanierQuery);
            lignePanierStmt.setInt(1, panierId);
            ResultSet lignePanierRs = lignePanierStmt.executeQuery();

            // Parcourir les lignes de panier et ajouter les livres correspondants à la liste
            while (lignePanierRs.next()) {
                int livreId = lignePanierRs.getInt("id_livre");
                int quantite = lignePanierRs.getInt("qte");
                String livreQuery = "SELECT libelle, image, categorie, prix FROM Livre WHERE id_livre=?";
                PreparedStatement livreStmt = cnx.prepareStatement(livreQuery);
                livreStmt.setInt(1, livreId);
                ResultSet livreRs = livreStmt.executeQuery();
                if (livreRs.next()) {
                    String image = livreRs.getString("image"); // Récupère l'URL de l'image depuis la base de données
                    data.add(new Livre(livreRs.getString("libelle"), image, livreRs.getString("categorie"), livreRs.getFloat("prix"), quantite));
                }
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
        quantite.setCellValueFactory(new PropertyValueFactory<Livre, String>("quantite"));

        table_panier.setItems(data);
    }
    
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
        showPanier();
    }

}
