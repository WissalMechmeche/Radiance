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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.UserService;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class AccountController implements Initializable {
 @FXML
    private Button btnmod;

    @FXML
    private Button btnretour;

    @FXML
    private TextField txtadress;

    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtmdp;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private TextField txttlfn;
    UserService se = new UserService();
      User user =new User();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     // TODO
    Affciher();
     
    }  
    void Affciher(){
    user =se.SelectUser(User.connecte);
     txtemail.setText(user.getEmail());
     txtnom.setText(user.getNom());
     txtprenom.setText(user.getPrenom()); 
     txtdate.setValue(user.getDateNaissance());
     txtadress.setText(user.getAdresse());
     txttlfn.setText(String.valueOf(user.getTel()));
    txtmdp.setText(user.getMotPasse());
    }
        @FXML
    void ModifierUser(ActionEvent event) throws IOException {
        User u=new User(Integer.valueOf(user.getId()) ,txtnom.getText(),txtprenom.getText(),
                       txtemail.getText(),txtadress.getText(),Integer.valueOf(txttlfn.getText()),
                       txtmdp.getText(),txtdate.getValue());

        try{
            se.updateOne(u);
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Acount");
            al.setHeaderText("Modification");
            al.setContentText("information modifiÃ©");
            al.show();
            Affciher();
        } catch (SQLException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void Retour(ActionEvent event) {
        Stage primaryStage = new Stage();
        System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));

        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/Acceuil.fxml"));
            Scene scene = new Scene(root, 650, 500);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    }
