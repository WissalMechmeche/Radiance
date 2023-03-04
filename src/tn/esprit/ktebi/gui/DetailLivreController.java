/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.LivreService;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class DetailLivreController implements Initializable {

    @FXML
    private Label libelle;
    @FXML
    private Text description;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         
    }    
    
    /*public void setTextFields(Livre L){
        libelle.setText(String.valueOf(L.getLibelle()));
        description.setText(String.valueOf(L.getDescription()));
 
        
    }*/
    
}
