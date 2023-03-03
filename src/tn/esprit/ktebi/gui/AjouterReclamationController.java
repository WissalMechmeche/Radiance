/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceReclamation;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class AjouterReclamationController implements Initializable {
    @FXML
    private Button btnInserer;

    @FXML
     TextArea txtRec;

    @FXML
    private Button btnimg;
    
    @FXML
    private ImageView imgview;
    
    @FXML
    private Label imgpath;
    private FileInputStream fis;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


    @FXML
    void InsererReclamtion(ActionEvent event) throws IOException, MessagingException {
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
                  

            Reclamation ev = new Reclamation(txtRec.getText(),LocalDate.now(),etat,u,rp,imgpath.getText());
            
            ServiceReclamation sr = new ServiceReclamation();
            try {
                sr.createOne(ev);
                TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Vous avez ajouter une nouvelle reclamation");
            tray.setMessage("Vous avez ajouter une nouvelle reclamation");
            tray.setNotificationType(NotificationType.INFORMATION);
            tray.showAndDismiss(Duration.millis(3000));
            
            Parent root = FXMLLoader.load(getClass().getResource("ListeReclamation.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Ajouter une Reclamations");
            stage.setScene(scene);
            stage.show();
            sendMail("akrimi.amine@esprit.tn","Reclamation envoyé avec succée",txtRec.getText());
            sendMail("ktebiktebi117@gmail.com","Nouvelle reclamation ",txtRec.getText());

            
            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur Interne");
                al.setContentText(ex.getMessage());
                al.show();
            }
            
        }
    }
    @FXML
    void AjouterImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgview.setImage(image);
            imgview.setFitWidth(200);
            imgview.setFitHeight(200);
            imgview.scaleXProperty();
            imgview.scaleYProperty();
            imgview.setSmooth(true);
            imgview.setCache(true);
            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fin.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
            byte[] person_image = bos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
        imgpath.setText(file.getAbsolutePath());
    } 
    
    public static void sendMail(String recipient, String sujet,String contenu) throws MessagingException {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "ktebiktebi117@gmail.com";
        String password = "suehilzqfolzmnzx";
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
         
    
}
