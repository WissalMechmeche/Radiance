/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import tn.esprit.ktebi.entities.Role;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.UserService;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HelloController implements Initializable {
    
    
    @FXML
    private Button log;

    @FXML
    private Button insc;
    @FXML
    private Button btntest;
    
     
    public ComboBox<String> comm;
           ObservableList<Role> list;

    UserService se = new UserService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

          try {
            // TODO
            list= FXCollections.observableList(se.RecupCombo());
            for(int i=0;i<list.size();i++){
            this.comm.getItems().add(list.get(i).getRole());
            }
        } catch (SQLException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
           
            primaryStage.setTitle("ktebi");
            primaryStage.setScene(scene);
            primaryStage.show();
            //AjouterUserFXMLController acont = new AjouterUserFXMLController();
            String s =comm.getValue();
            //acont.setrole(s);
          
    }
       
    
}