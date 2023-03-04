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
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.LignePanier;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author Pc Anis
 */
public class ContenuPanierController implements Initializable {

    private Connection cnx;
    @FXML
    private Button passer_cmd;
    @FXML
    private TextField nom_livre;
    @FXML
    private TextField cat;
    @FXML
    private TextField prixx;
    @FXML
    private TextField quantitee;
    @FXML
    private Button modif_qte;

    ServiceLignePanier lp = new ServiceLignePanier();
    @FXML
    private Button supp;

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
    private Label mnt_tot;

    @FXML
    private TableColumn<Livre, Integer> quantite;
    public ObservableList<Livre> data;

    public void showPanier() {
        try {
            // Récupérer le panier de l'utilisateur
            String panierQuery = "SELECT * FROM Panier WHERE id_user=?";
            PreparedStatement panierStmt = cnx.prepareStatement(panierQuery);
            int userId = 1;
            panierStmt.setInt(1, userId);

            ResultSet panierRs = panierStmt.executeQuery();

            // Si l'utilisateur n'a pas de panier
            if (!panierRs.next()) {
                System.out.println("Le panier de l'utilisateur est vide.");
                return;
            }

            // Récupérer les lignes de panier pour le panier de l'utilisateur
            float prixTotal = 0.0f;
            int panierId = panierRs.getInt("id_panier");
            String lignePanierQuery = "SELECT * FROM ligne_panier WHERE id_panier=?";
            prixTotal = lp.calculerPrixTotal(panierId);
            mnt_tot.setText("Montant total: " + String.valueOf(prixTotal) + "DT");

            PreparedStatement lignePanierStmt = cnx.prepareStatement(lignePanierQuery);
            lignePanierStmt.setInt(1, panierId);
            ResultSet lignePanierRs = lignePanierStmt.executeQuery();

            // Parcourir les lignes de panier et ajouter les livres correspondants à la liste
            data.clear();

            while (lignePanierRs.next()) {
                int livreId = lignePanierRs.getInt("id_livre");
                int qte = lignePanierRs.getInt("qte");
                String livreQuery = "SELECT libelle, image, categorie, prix FROM Livre WHERE id_livre=?";
                PreparedStatement livreStmt = cnx.prepareStatement(livreQuery);
                livreStmt.setInt(1, livreId);
                ResultSet livreRs = livreStmt.executeQuery();
                if (livreRs.next()) {
                    String image = livreRs.getString("image");
                    float prix = livreRs.getFloat("prix");

                    data.add(new Livre(livreRs.getString("libelle"), image, livreRs.getString("categorie"), livreRs.getFloat("prix"), qte));

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
        quantite.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("id"));

        table_panier.setItems(data);

    }

    public void showPanierPage(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("cart-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    public void showHome(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeCart.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    private void GotoFacturePage(ActionEvent event) throws IOException {
        URL url = new File("C:\\Users\\Pc Anis\\Documents\\GitHub\\Radiance\\src\\tn\\esprit\\ktebi\\gui\\Facture-ui.fxml").toURI().toURL();
        Parent panierPage = FXMLLoader.load(url);

        Scene panierPageScene = new Scene(panierPage);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(panierPageScene);
        appStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources
    ) {
        data = FXCollections.observableArrayList();

        showPanier();

    }

    @FXML
    private void SelectItems(MouseEvent event) {
        nom_livre.clear();

        cat.clear();
        prixx.clear();
        quantitee.clear();
        nom_livre.setDisable(true);
        cat.setDisable(true);
        prixx.setDisable(true);
        quantitee.setDisable(false);
        nom_livre.appendText(table_panier.getSelectionModel().getSelectedItem().getLibelle());
        cat.appendText(table_panier.getSelectionModel().getSelectedItem().getCategorie());
        prixx.appendText(Float.toString(table_panier.getSelectionModel().getSelectedItem().getPrix()));
        quantitee.appendText(Integer.toString(table_panier.getSelectionModel().getSelectedItem().getId()));

    }

    @FXML
    private void onModifierButtonClick(ActionEvent event) throws SQLException, NullPointerException {
        try {
            int id_user = 1;
            Livre ligneSelectionnee = table_panier.getSelectionModel().getSelectedItem();
            ServicePanier sp = new ServicePanier();
            Livre livre = sp.getLivreByLibelle(ligneSelectionnee.getLibelle());
            System.out.println(livre);

            int nouvelleQuantite = Integer.parseInt(quantitee.getText());

            Panier panier = sp.getPanierByUser(id_user);

            List<LignePanier> lignesPanier = lp.getLignePanierByLivre(livre, panier);
            System.out.println(lignesPanier);

            for (LignePanier ligne : lignesPanier) {
                ligne.setQuantite(nouvelleQuantite);
                lp.modifierLignePanier(ligne);
            }
            // Mettre à jour le tableau
            showPanier();

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quantité modifiée");
            alert.setHeaderText(null);
            alert.setContentText("La quantité a été modifiée avec succès!");
            alert.showAndWait();
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun livre sélectionné");
            alert.setContentText("Veuillez sélectionner un livre dans la table pour le modifié!");
            alert.showAndWait();
        }
    }

    @FXML
    private void deletehandler(ActionEvent event) throws SQLException, NullPointerException {
        try {

            Livre selectedLivre = table_panier.getSelectionModel().getSelectedItem();
            ServicePanier sp = new ServicePanier();
            selectedLivre = sp.getLivreByLibelle(selectedLivre.getLibelle());

            if (selectedLivre == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Aucun livre sélectionné");
                alert.setContentText("Veuillez sélectionner un livre dans la table !");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer l'élément " + selectedLivre.getLibelle() + " du panier ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                lp.supprimerLignePanier(selectedLivre);
                showPanier();
                nom_livre.clear();
                cat.clear();
                prixx.clear();
                quantitee.clear();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Ligne supprimée!");
                alert2.setHeaderText(null);
                alert2.setContentText("Le livre a été supprimer du ligne du panier!");
                alert2.showAndWait();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun livre sélectionné");
            alert.setContentText("Veuillez sélectionner un livre dans la table !");
            alert.showAndWait();
        }
    }

    private void showFacture(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("facture-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void GotoFacturePage(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/ktebi/gui/Facture-ui.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene((scene));
        stage.show();
    }

}