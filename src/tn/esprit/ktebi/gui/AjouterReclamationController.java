/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceReclamation;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class AjouterReclamationController implements Initializable {
    @FXML
    private Button btnInserer;

    @FXML
    private TextArea txtRec;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    @FXML
    void InsererReclamtion(ActionEvent event) throws IOException {
       if (txtRec.getText().equals("")) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Controle de saisie");
            al.setHeaderText("Erreur de saisie !");
            al.setContentText("Le contenu est vide !");
            al.show();
        }else{
        String etat ="en cours";
        ReponseReclamation rp = new ReponseReclamation(1);
        User u=new User(5);
            Reclamation ev = new Reclamation(txtRec.getText(),LocalDate.now(),etat,u,rp);
            
            ServiceReclamation sr = new ServiceReclamation();
            try {
                sr.createOne(ev);
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Succés");
                al.setHeaderText("Reclamation envoyé");
                al.show();
                Stage primaryStage = new Stage();
            System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));
            
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/ListeReclamation.fxml"));
            Scene scene = new Scene(root, 650, 500);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur Interne");
                al.setContentText(ex.getMessage());
                al.show();
            }
            
        }
    }    
    
}
