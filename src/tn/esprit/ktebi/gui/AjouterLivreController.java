/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;



import java.io.IOException;

import java.net.URL;
import java.security.Security;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.LivreServicee;
import tn.esprit.ktebi.services.PromoService;
import tn.esprit.ktebi.services.UserService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class AjouterLivreController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private Button btnAjout;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfLibelle;
    @FXML
    private TextField tfEditeur;
    @FXML
    private TextField tfCategorie;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfLangue;
    @FXML
    private ImageView image;
    @FXML
    private Button btnAnnuler;
    @FXML
    private ComboBox<String> auteurs;
    @FXML
    private ComboBox<String> promos;
    
    LivreServicee ls = new LivreServicee();
    
    UserService us = new UserService();

    List<User> listU = null;
    
    PromoService ps = new PromoService();

    List<Promo> listP = null;

    Livre livre =null ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            listU = us.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(AjouterLivreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            List<String> nomsAuteurs = new ArrayList<>();
            for (User auteur : listU) {
                nomsAuteurs.add(auteur.getPrenom());
            }
            auteurs.setItems(FXCollections.observableArrayList(nomsAuteurs));
            
            
        try {
            listP = ps.selectAll();
        } catch (SQLException ex) {
            Logger.getLogger(AjouterLivreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            List<String> pr = new ArrayList<>();
            for (Promo promo : listP) {
                pr.add(promo.getCode());
            }
            promos.setItems(FXCollections.observableArrayList(pr));
        
        
    }    

    @FXML
    private void AjoutLivre(ActionEvent event) throws SQLException, IOException  {
        if(tfLibelle.getText().isEmpty()
                || taDescription.getText().isEmpty()
                || tfCategorie.getText().isEmpty()
                || tfEditeur.getText().isEmpty()
                || tfPrix.getText().isEmpty()
                || date.getValue()== null
                || auteurs.getSelectionModel().getSelectedItem()== null
                || promos.getSelectionModel().getSelectedItem()== null)
        {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            
            al.setTitle("Contrôle de saisie");
            al.setHeaderText("Erreur de saisie");
            al.setContentText("Les données sont vides !");
            al.show();
        }

        else if(isValidPrice(tfPrix.getText())== false)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Le prix doit être un nombre.");
            alert.showAndWait();
        }
       
        
        
        String libelle = tfLibelle.getText();
        String description = taDescription.getText();
        String editeur = tfEditeur.getText();
        String categorie = tfCategorie.getText();
        float prix = Float.parseFloat(tfPrix.getText());
        String langue = tfLangue.getText();
        LocalDate dateEdition = date.getValue();
        
        String auteurSelectionnee = auteurs.getValue();
        
        User aut = null ;
        for (User auteur : listU) {
            String temp = auteur.getPrenom() ;
            if(temp == auteurSelectionnee)
            {
                aut = new User(auteur.getId(),auteur.getNom(),auteur.getPrenom(),auteur.getEmail());
                System.out.println(aut);
            }
                
        }
         Promo pr = null ;
        
        String codeSelectionnee = promos.getValue();
        
         for (Promo promo : listP) {
            String temp = promo.getCode() ;
            if(temp == codeSelectionnee)
            {
                pr = promo ;
                System.out.println(pr);
            }
                
        }
         
         livre = new Livre(libelle, description,editeur,new java.sql.Date(Date.valueOf(dateEdition).getTime()),categorie,prix,langue,pr, aut,"");
          
          System.out.println(livre);
          
          
        ls.create(livre);
        
         Alert al = new Alert(Alert.AlertType.INFORMATION);

            al.setTitle("Livre ajouté");
            al.setHeaderText("");
            al.setContentText("Le livre " + livre.getLibelle() + " a été ajouté avec succès !");
            al.show();
        
        // Envoi de l'e-mail de notification
        String recipient = "recipient@example.com";
        String subject = "Nouveau livre ajouté";
        String body = "Bonjour,\n Un nouveau livre intitulé :"+livre.getLibelle()+" a été ajouté à la libraire ktebi.";
        
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        sendMail(); // Gérer l'erreur d'envoi de l'e-mail
        
    

        // Recharger la vue de la liste des livres
        Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/ktebi/gui/Liste_livres.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
 

    public void sendMail() throws SQLException {
       
        List<User> clients = us.getAllClients();
        for(User user : clients)
        {
             int userid = user.getId();
             
             User client = us.getUserById(userid);
                
        System.out.println(client.getEmail());

        if (client == null) {
            System.out.println("User with ID " + userid + " not found!");
            return;
        } else {
        }

        final String username = "wissal.mechmeche@esprit.tn";
        final String password = "223JFT5669";
        
        String recipientEmail = client.getEmail();
        
        String subject = "Ajout Livre notification";
        String message = "Bonjour,\n Un nouveau livre intitulé :"+livre.getLibelle()+" a été ajouté à la libraire ktebi.";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
       Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
       
       
       
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            System.out.println("Email notification sent successfully.");
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }

        }
        
    }



        

        
    
    
    private boolean isValidPrice(String price) {
    String regex = "^\\d+(\\.\\d{1,2})?$"; // Expression régulière pour un nombre décimal avec au maximum 2 chiffres après la virgule
    return price.matches(regex);
}

    @FXML
    private void Annuler(ActionEvent event) {
        tfLibelle.setText("");
        taDescription.setText("");
        tfEditeur.setText("");
        tfCategorie.setText("");
        date.setValue(null);
        tfLangue.setText("");
        auteurs.setValue(null);
        promos.setValue(null);
        tfPrix.setText("");
        //btnAjout.getScene().getWindow().hide();
    }
    
}
