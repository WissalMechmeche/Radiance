/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.service.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class FXMLhController implements Initializable {
    
    
    @FXML
    private Button log;

    @FXML
    private Button insc;
    
      @FXML
    private ComboBox<String> comm;

ServiceUser usr = new ServiceUser();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comm.setItems(usr.RecupCombo());
        
    }  
    
    @FXML
    public void tologin(ActionEvent event) throws IOException{
    
    
       Stage primaryStage = new Stage();
                System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));
                 Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/LoginFXML.fxml"));
            Scene scene = new Scene(root, 650, 500);
            //AcountFXMLController acont = new AcountFXMLController();
            //acont.getId(id);
            primaryStage.setTitle("ktebi");
            primaryStage.setScene(scene);
            primaryStage.show();
    
    }
      @FXML
      public void toSignup(ActionEvent event) throws IOException{
    
    
       Stage primaryStage = new Stage();
                System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));
                 Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/AjouterUserFXML.fxml"));
            Scene scene = new Scene(root, 650, 500);
            //AcountFXMLController acont = new AcountFXMLController();
            //acont.getId(id);
            primaryStage.setTitle("ktebi");
            primaryStage.setScene(scene);
            primaryStage.show();
    
    }
       
    
}
