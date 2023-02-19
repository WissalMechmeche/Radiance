/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
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
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.LivreService;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterLivreController implements Initializable {

    @FXML
    private TextField tfLibelle;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfEditeur;
    @FXML
    private TextField tfCategorie;
    @FXML
    private TextField tfLangue;
    @FXML
    private TextField tfPrix;
    @FXML
    private DatePicker date;
    @FXML
    private Button btnAjout;
    @FXML
    private TextField tfPromo;
    @FXML
    private TextField tfAuteur;
    @FXML
    private TextField tfImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AjouterLivre(ActionEvent event) {
        if(tfLibelle.getText().isEmpty() 
                || taDescription.getText().isEmpty() 
                || tfCategorie.getText().isEmpty()
                || tfLangue.getText().isEmpty()
                || tfPrix.getText().isEmpty()
                || date.getAccessibleText().isEmpty()
                || tfPromo.getText().isEmpty()
                || tfAuteur.getText().isEmpty()
                )
        {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            
            al.setTitle("Contrôle de saisie");
            al.setHeaderText("Erreur de saisie");
            al.setContentText("Les données sont vides !");
            al.show();
        }
        else
        {
            //Livre l = new Livre(tfLibelle.getText(),taDescription.getText(),tfEditeur.getText(),tfCategorie.getText(),Float.parseFloat(tfPrix.getText()),tfLangue.getText(),tfImage.getText() );
            
            LivreService ls = new LivreService();
            
            
    }
    
}
}
