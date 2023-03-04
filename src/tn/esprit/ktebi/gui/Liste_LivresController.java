/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.LivreServicee;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class Liste_LivresController implements Initializable {

    @FXML
    private TableColumn<Livre, String> libelle;
    
    private TableColumn<Livre, String> editeur;
    @FXML
    private TableColumn<Livre, String> categorie;
    
    @FXML
    private TableColumn<Livre, Float> prix;
    
    @FXML
    private TableColumn<Livre, Date> date;
    
    @FXML
    private TableView<Livre> tvLivres;
    
    
    
    @FXML
    private Button btnRedirectionAjout;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private TextField tfRecherche;
    @FXML
    private Button btnRechercher;
    @FXML
    private Button btnDetail;
    @FXML
    private ComboBox<String> combo;
    
    
    LivreServicee ls = new LivreServicee();
    
    
    public static ObservableList<Livre> listL = null;
    
    Livre livre = null ;
    
    
   

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
        ObservableList<String> list = FXCollections.observableArrayList("Tous les livres","Libelle", "Catégorie");
        
             combo.setItems(list);
        try {
            displayLivres();
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }   
    
    
    
    public void displayLivres() throws SQLException{
        
        
        libelle.setCellValueFactory(new PropertyValueFactory("libelle"));
        date.setCellValueFactory(new PropertyValueFactory("date_edition"));
        categorie.setCellValueFactory(new PropertyValueFactory("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory("prix"));
       
        List l = ls.selectAll();
        
        listL =FXCollections.observableArrayList(l);
        
        tvLivres.setItems(listL);
        
    }

    @FXML
    private void RedirectionAjout(ActionEvent event) throws IOException {

        btnRedirectionAjout.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader
                            .load(getClass().getResource("/tn/esprit/ktebi/gui/AjouterLivre.fxml"));
                    Scene scene = new Scene(root, 1100, 900);
            
                    Stage stage = new Stage();
                    stage.setTitle("Ajouter Livre ");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(Liste_LivresController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            }
        });
    }
    
    public void updateListeLivres() throws SQLException {
        // Récupérer la liste des livres de la base de données
        List<Livre> livres = ls.selectAll();

        // Mettre à jour la table view
        tvLivres.setItems(FXCollections.observableArrayList(livres));
    }

    @FXML
    private void SupprimerLivre(ActionEvent event) throws SQLException {
        Livre livre = tvLivres.getSelectionModel().getSelectedItem();
        if (livre == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aucun livre sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un livre dans la liste.");
            alert.showAndWait();
    }
        else
        {
             Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Voulez-vous vraiment supprimer le livre :" + livre.getLibelle());
            a.setTitle("Confirmer");
            Optional<ButtonType> res = a.showAndWait();
            if (res.get() == ButtonType.OK) {
                listL.remove(livre);

                LivreServicee ls = new LivreServicee();
                ls.delete(livre.getId());

            }
        }
       
    }

    @FXML
    private void modifierLivre(ActionEvent event)  {
        
    // Vérifier si un livre est sélectionné
        Livre livre = tvLivres.getSelectionModel().getSelectedItem();
        if (livre == null) {
            // Afficher un message d'erreur si aucun livre n'est sélectionné
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun livre sélectionné");
            alert.setContentText("Veuillez sélectionner un livre dans la liste.");
            alert.showAndWait();
            return;
        }

        try {
            // Charger la vue ModifierLivre.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/ktebi/gui/ModifierLivre.fxml"));
            Parent root = loader.load();
            ModifierLivreController controller = loader.getController();

            // Passer le livre à modifier au contrôleur de la vue ModifierLivre.fxml
            controller.setLivre(livre);

            // Afficher la vue ModifierLivre.fxml
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Afficher un message d'erreur en cas d'erreur de chargement de la vue ModifierLivre.fxml
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de chargement de la vue");
            alert.setContentText("Une erreur est survenue lors du chargement de la vue ModifierLivre.fxml : " + e.getMessage());
            alert.showAndWait();
        }
    }


       
        
        


    @FXML
    private void detailLivre(ActionEvent event) throws SQLException {
        
        
        tvLivres.setOnMouseClicked(event1 -> {
    // Vérifier si l'événement est un double-clic
    if (event1.getClickCount() == 2) {
                // Récupérer le livre sélectionné dans la TableView
                Livre livreSelectionne = tvLivres.getSelectionModel().getSelectedItem();

                // Vérifier si un livre a été sélectionné
                if (livreSelectionne != null) {
                    // Créer une nouvelle fenêtre de dialogue pour afficher les détails du livre
                    Stage detailsStage = new Stage();
                    detailsStage.setTitle("Détails du livre");

                    // Créer des labels pour afficher les informations du livre
                    Label labelLibelle = new Label("Libellé: " + livreSelectionne.getLibelle());
                    Label labelDescription = new Label("Description: " + livreSelectionne.getDescription());
                    Label labelAuteur = new Label("Auteur: " + livreSelectionne.getAuteur().getNom());
                    Label labelEditeur = new Label("Editeur: " + livreSelectionne.getEditeur());
                    Label labelCategorie = new Label("Catégorie: " + livreSelectionne.getCategorie());
                    Label labelDateEdition = new Label("Date d'édition: " + livreSelectionne.getDate_edition().toString());
                    Label labelCodePromo = new Label("Code promo: " + livreSelectionne.getPromo().getCode());
                    Label labelPrix = new Label("Prix: " + livreSelectionne.getPrix() + " DT");
                    /*ImageView imageView = new ImageView();
                    try {
                        // Convertir le tableau de bytes de l'image en objet Image
                        Image image = new Image(new ByteArrayInputStream(livreSelectionne.getImage()));

                        // Afficher l'image dans l'ImageView
                        imageView.setImage(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
*/
                    // Créer un VBox pour ajouter les labels et l'ImageView
                    VBox vbox = new VBox(10);
                    vbox.getChildren().addAll(labelLibelle, labelDescription, labelAuteur, labelEditeur, labelCategorie,
                            labelDateEdition, labelCodePromo, labelPrix);

                    // Créer une nouvelle scène avec le VBox comme racine
                    Scene scene = new Scene(vbox, 400, 500);

                    // Afficher la scène dans la nouvelle fenêtre de dialogue
                    detailsStage.setScene(scene);
                    detailsStage.show();
                }
            }
        });
    }

    @FXML
    private void chercherLivre(ActionEvent event) throws SQLException {

        String mot = tfRecherche.getText();
        
        String filtre = combo.getValue();

        if (filtre=="Libelle") {
            List<Livre> livres = ls.searchByLibelle(mot);
            tvLivres.getItems().clear();
            tvLivres.getItems().addAll(livres);
        }
        else if(filtre == "Catégorie")
        {
            List<Livre> livres = ls.searchByCategorie(mot);
            tvLivres.getItems().clear();
            tvLivres.getItems().addAll(livres);
        }
        else
        {
            List<Livre> livres = ls.selectAll();
            tvLivres.getItems().clear();
            tvLivres.getItems().addAll(livres);
        }
        

    }


    
    
    
}
