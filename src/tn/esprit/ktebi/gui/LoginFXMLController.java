/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.service.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class LoginFXMLController implements Initializable {
    ServiceUser se = new ServiceUser();

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtmdp;
    @FXML
    private Button btnLogin;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    @FXML
    void Login(ActionEvent event) {
        try {
            Integer u=se.Login(txtemail.getText(),txtmdp.getText()); 
            if(u != null){
                    Stage primaryStage = new Stage();
                System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));

        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/AcceuilFXML.fxml"));
            Scene scene = new Scene(root, 650, 500);
            AcountFXMLController acont = new AcountFXMLController();
            acont.getId(u);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }else{
                     System.out.println("noooo");
       
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
 
        }

    }

    
}
