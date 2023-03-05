/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.service.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AcceuilController implements Initializable {

    private Integer id;
//      @FXML
//    private Button btnAccount;

      ServiceUser se = new ServiceUser();
      User user =new User();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    }    
  
    @FXML
    void AfficherAccount(ActionEvent event) {
        //se.SelectUser(id);
        Stage primaryStage = new Stage();
        System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));

        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/AcountFXML.fxml"));
            Scene scene = new Scene(root, 650, 500);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}


