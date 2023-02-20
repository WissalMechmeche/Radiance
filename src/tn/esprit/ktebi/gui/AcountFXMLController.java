/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.service.ServiceUser;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class AcountFXMLController implements Initializable {
 @FXML
    private Button btnmod;

    @FXML
    private Button btnretour;

    @FXML
    private TextField txtadress;

    @FXML
    private TextField txtconmdp;

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
    ServiceUser se = new ServiceUser();
    Integer id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

       // txttlfn.setText(String.valueOf(l.getId()));
        /*
        txtnom.setText(user.getNom());
        txtprenom.setText(user.getPrenom());
        txtadress.setText(user.getAdresse());
        txtemail.setText(user.getEmail());
        txtdate.setValue(user.getDateNaissance());
        txttlfn.setText(String.valueOf(user.getTel()));
        txtmdp.setText(user.getMotPasse());*/
    }    
        @FXML
    void ModifierUser(ActionEvent event) {

    }

    @FXML
    void Retour(ActionEvent event) {

    }
    public void getId(Integer id){
     User user =new User();

        try{
        user =se.SelectUser(id);

        System.out.println(user);
    } catch (SQLException ex) {
            Logger.getLogger(AcountFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
