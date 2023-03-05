/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
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
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
      @FXML
      private Button btnOublie; 
             @FXML
      private Button btnRetour ;
    static int tent;
   
      
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
  void verifMail(){
      
  }
    @FXML
    void Login(ActionEvent event) throws MessagingException {
        
          if(tent >=3)  {
              sendMail(txtemail.getText(),"Alert tentative","vous avez depasser 3 tentives faites attention,vous etes bloquer pour le moment vous dever redemarrer l'application");
          }else{
            try {
            Integer u=se.Login(txtemail.getText(),txtmdp.getText());
            if(!se.VerifMail(txtemail.getText())){
                System.out.println("email n'existe pas");
            }else if(se.VerifStatus(txtemail.getText())){
                System.out.println("compte desactiver");

            }
            else if(u != null){
                Stage primaryStage = new Stage();
                System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));
               tent=0; 
        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/Acceuil.fxml"));
            Scene scene = new Scene(root, 650, 500);
            primaryStage.setTitle("Ktebi");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        }else if (se.VerifMail(txtemail.getText())) {
                tent++;
                     System.out.println("mdp incorrecte");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
 
        }
          }

    }
    public static void sendMail(String recipient, String sujet,String contenu) throws MessagingException {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "ktebiktebi117@gmail.com";/*hedha mail eli bch yetb3ath meno l email*/
        String password = "suehilzqfolzmnzx"; /* hedha l mot de passe*/
        Session session = Session.getInstance(properties, new Authenticator() {
             @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(myAccountEmail, password);
            }
        });
            
        Message message = prepareMessage(session, myAccountEmail, recipient,sujet, contenu);

        javax.mail.Transport.send(message);
        System.out.println("Message sent successfully");
    }  
   
    
    
    private static Message prepareMessage(Session session, String myAccountEmail, String recipient,String sujet, String contenu) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(sujet);
            message.setText("Contenu:\n"+ contenu);
            return message;
        } catch (MessagingException ex) {
          
        }
        return null;} 
             
    
    @FXML
     void Retour(ActionEvent event) {
        Stage primaryStage = new Stage();
        System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));

        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/FXMLh.fxml"));
            Scene scene = new Scene(root, 650, 500);

            primaryStage.setTitle("Retour");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

       @FXML
        void MotOublie (ActionEvent event) {
         Stage primaryStage = new Stage();
        System.out.println(getClass().getResource("/tn/esprit/ktebi/gui/"));

        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("../gui/Motdepasseoublie.fxml"));
            Scene scene = new Scene(root, 650, 500);

            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
      
    }

