/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Role;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.service.ServiceUser;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AjouterUserFXMLController implements Initializable {
    @FXML
    private Button btnajouter;

    @FXML
    private Button btnact;

    @FXML
    private Button btnretour;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private TextField txtadress;

    @FXML
    private TextField txtemail;
   @FXML
    private TextField txttlfn;

    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtmdp;

    @FXML
    private TextField txtconmdp;
    Integer id;
    ServiceUser se = new ServiceUser();
        private Connection cnx;
     public AjouterUserFXMLController(){
        cnx = MaConnexion.getInstance().getCnx();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TO        

    }    
        @FXML
    void Actualiser(ActionEvent event) {
        /*txtnom.setText("");
        txtprenom.setText("");
        txtadress.setText("");
        txtemail.setText("");
        txtdate.setValue(null);*/
        Role u = new Role();
        User user = new User(txtnom.getText()
                    , txtprenom.getText(),txtemail.getText(),txtadress.getText(),txtdate.getValue()
                    ,Integer.parseInt(txttlfn.getText()),txtmdp.getText(),u);
        try{
            se.updateOne(user);
            System.out.println("yes");

        } catch (SQLException ex) {
            Logger.getLogger(AjouterUserFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void AjouterUtilisateur(ActionEvent event) {
    
        if (txtnom.getText().isEmpty() 
                || txtprenom.getText().isEmpty()
                || txtadress.getText().isEmpty() || txtdate.getValue()==null) {
            
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Controle de saisie");
            al.setHeaderText("Erreur de saisie !");
            al.setContentText("Les données sont vides !");
            al.show();
            
        }
        else if (!VerifEmail(txtemail.getText())){
         Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Format email");
            al.setHeaderText("Erreur de saisie !");
            al.setContentText("Les données sont vides !");
            al.show();
        
        
        }
        
        else{
            Role r = new Role() ;
            r.setId(1);
            User p = new User(txtnom.getText()
                    , txtprenom.getText(),txtemail.getText(),txtadress.getText(),txtdate.getValue()
                    ,Integer.parseInt(txttlfn.getText()),txtmdp.getText(),r);
            
            ServiceUser sp = new ServiceUser();
            try {
                sp.createOne(p);
                  Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("suscces");
            al.setHeaderText("perssone ajoutée");
            al.setContentText("Les données sont vides !");
            al.show();
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
            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur Interne");
                al.setContentText(ex.getMessage());
                al.show();
            } catch (IOException ex) {
                Logger.getLogger(AjouterUserFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
           public  boolean VerifEmail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 

    @FXML
    void Retour(ActionEvent event) {
       
    }
  
}
