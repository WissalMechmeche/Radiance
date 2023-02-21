/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.ktebi.entities.Livre;

import tn.esprit.ktebi.services.LivreService;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ModifierLivreController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfAuteur;
    @FXML
    private TextField tfImage;
    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfEditeur;
    @FXML
    private TextField tfCategorie;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfLangue;
    @FXML
    private TextField tfPromo;
    
   
    @FXML
    private Button btnModifier;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ModifierLivre(ActionEvent event) throws SQLException {
        
       LivreService ls= new LivreService();
        
        
         String libelle= tfLibelle.getText();
         String description= taDescription.getText();
         String editeur = tfEditeur.getText();
         
         String auteur = tfAuteur.getText();
         String image = tfImage.getText();
         
         String langue = tfLangue.getText();
         
         String categorie = tfCategorie.getText();
         
         Float prix = Float.parseFloat(tfPrix.getText());
         
         Date date1 = Date.valueOf(date.toString());
         
         Integer promo = Integer.parseInt(tfPromo.getText());
         
        Livre L;
           //L = new Livre(libelle,description,editeur,date1,categorie,prix,langue,image,promo,auteur);
           //ls.update(L);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Travel Me :: Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Livre modifié");
                alert.showAndWait();  
        
    }
    
    public void setTextFields(Livre L){
        tfLibelle.setText(String.valueOf(L.getLibelle()));
        taDescription.setText(String.valueOf(L.getDescription()));
        tfEditeur.setText(String.valueOf(L.getEditeur()));
        tfAuteur.setText(String.valueOf(L.getAuteur()));
        tfCategorie.setText(String.valueOf(L.getCategorie()));
        tfLangue.setText(String.valueOf(L.getLangue()));
        tfImage.setText(String.valueOf(L.getImage()));
        tfPrix.setText(String.valueOf(L.getPrix()));
        tfPromo.setText(String.valueOf(L.getPromo()));
        
    }
    
}
