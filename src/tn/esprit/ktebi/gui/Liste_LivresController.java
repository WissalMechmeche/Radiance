/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.LivreService;

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
    private Button btnRedirectionAjout;
    @FXML
    private TableView<Livre> tvLivres;
    
    
    LivreService ls = new LivreService();
    public static ObservableList<Livre> listL = null;
    
    @FXML
    private TableColumn<Livre, Date> date;
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
    
    Livre livre = null ;
    
    
   

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        try {
            displayLivres();
            
            ObservableList<String> list = FXCollections.observableArrayList("Libelle", "Catégorie");
        
             combo.setItems(list);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }   
    
    
    
    public void displayLivres() throws SQLException{
        
        ImageView delete = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/supp.png").toExternalForm());
        btnSupprimer.setGraphic(delete);
        
        ImageView add = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/ajouter.png").toExternalForm());
        btnRedirectionAjout.setGraphic(add);
        
        ImageView edit = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/modifier.png").toExternalForm());
        btnModifier.setGraphic(edit);
        
        ImageView detail = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/detail.jpg").toExternalForm());
        btnDetail.setGraphic(detail);
        
        ImageView search = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/recherche.png").toExternalForm());
        btnRechercher.setGraphic(search);
        
        
        
        	
        libelle.setCellValueFactory(new PropertyValueFactory("libelle"));
        date.setCellValueFactory(new PropertyValueFactory("date_edition"));
        categorie.setCellValueFactory(new PropertyValueFactory("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory("prix"));
       
        List l = ls.selectAll();
        
        listL =FXCollections.observableArrayList(l);
        
        tvLivres.setItems(listL);
        
    }

    @FXML
    private void RedirectionAjout(ActionEvent event) {
        
        btnRedirectionAjout.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader
                            .load(getClass().getResource("/tn/esprit/ktebi/gui/AjouterLivre.fxml"));
                    Scene scene = new Scene(root, 1000, 700);
            
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

    @FXML
    private void SupprimerLivre(ActionEvent event) throws SQLException {
        Livre l = (Livre) tvLivres.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Voulez-vous vraiment supprimer le livre :"+l.getLibelle());
        a.setTitle("Confirmer");
        Optional<ButtonType> res = a.showAndWait();
        if(res.get() == ButtonType.OK)
        {
            listL.remove(l);
            
            LivreService ls = new LivreService();
            ls.delete(l.getId());
            
        }
    }

    @FXML
    private void modifierLivre(ActionEvent event) throws SQLException {
        
        livre = tvLivres.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/tn/esprit/ktebi/gui/ModifierLivre.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        ModifierLivreController m = loader.getController();
        // mrc.setUpdate(true);
        m.setTextFields(livre);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        displayLivres();
        
    }

    @FXML
    private void detailLivre(ActionEvent event) {
    }

    @FXML
    private void chercherLivre(ActionEvent event) {
        
        btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                ImageView delete = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/supp.png").toExternalForm());
        btnSupprimer.setGraphic(delete);
        
        ImageView add = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/ajouter.png").toExternalForm());
        btnRedirectionAjout.setGraphic(add);
        
        ImageView edit = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/modifier.png").toExternalForm());
        btnModifier.setGraphic(edit);
        
        ImageView detail = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/detail.jpg").toExternalForm());
        btnDetail.setGraphic(detail);
        
        ImageView search = new ImageView(getClass().getResource("/tn/esprit/ktebi/ressources/images/recherche.png").toExternalForm());
        btnRechercher.setGraphic(search);
        
        
        
        	
        libelle.setCellValueFactory(new PropertyValueFactory("libelle"));
        date.setCellValueFactory(new PropertyValueFactory("date_edition"));
        categorie.setCellValueFactory(new PropertyValueFactory("categorie"));
        prix.setCellValueFactory(new PropertyValueFactory("prix"));
        
                try {
                    String filtre = combo.getSelectionModel().getSelectedItem();
                    if(filtre == "Libelle")
                    {
                        List l = ls.searchByLibelle(tfRecherche.getText());
                        listL =FXCollections.observableArrayList(l);
                    } 
                    else {
                        List l = ls.searchByCategorie(tfRecherche.getText());
                        listL =FXCollections.observableArrayList(l);
                        
                    }
       
                    
                    tvLivres.setItems(listL);
                } catch (SQLException ex) {
                    Logger.getLogger(Liste_LivresController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
            }
        });
        
    }

    
    
    
}
