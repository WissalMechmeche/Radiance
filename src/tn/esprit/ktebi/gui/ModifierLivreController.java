/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.entities.User;

import tn.esprit.ktebi.services.LivreServicee;
import tn.esprit.ktebi.services.PromoService;
import tn.esprit.ktebi.services.UserService;



/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ModifierLivreController implements Initializable {

    @FXML
    private DatePicker date;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfImage;
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
    private Button btnModifier;
    @FXML
    private ComboBox<String> auteurs;
    @FXML
    private ComboBox<String> promos;
    
    LivreServicee ls = new LivreServicee();
    
    UserService us = new UserService();

    List<User> listU = null;
    
    PromoService ps = new PromoService();

    List<Promo> listP = null;
    
    private Livre livre;
    
    

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
    private void ModifierLivre(ActionEvent event) throws SQLException, IOException {
        
        // Récupérer les nouvelles valeurs des champs de texte
        String libelle = tfLibelle.getText();
        String description = taDescription.getText();
        String editeur = tfEditeur.getText();
        String langue = tfLangue.getText();
        String categorie = tfCategorie.getText();
        float  prix = Float.parseFloat(tfPrix.getText());
        String auteurSelectionnee = auteurs.getValue();
        String codeSelectionnee = promos.getValue();
        
        LocalDate date_edition = date.getValue();
        
       
        if (tfLibelle.getText().isEmpty()
                || taDescription.getText().isEmpty()
                || tfCategorie.getText().isEmpty()
                || tfEditeur.getText().isEmpty()
                || tfPrix.getText().isEmpty()
                || date.getValue() == null
                || auteurs.getSelectionModel().getSelectedItem() == null
                || promos.getSelectionModel().getSelectedItem() == null) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);

            al.setTitle("Contrôle de saisie");
            al.setHeaderText("Erreur de saisie");
            al.setContentText("Les données sont vides !");
            al.show();
        }
        
        

        // Créer un nouveau livre avec les nouvelles valeurs
        Livre nouveauLivre = new Livre();
        
        nouveauLivre.setId(livre.getId());
        nouveauLivre.setLibelle(libelle);
        nouveauLivre.setDescription(description);
        nouveauLivre.setEditeur(editeur);
        nouveauLivre.setCategorie(categorie);
        nouveauLivre.setLangue(langue);
        nouveauLivre.setDate_edition(new java.sql.Date(Date.valueOf(date_edition).getTime()));
        nouveauLivre.setPrix(prix);
        
       
        
        User a = null ;
        for (User aut : listU) {
            String temp = aut.getPrenom() ;
            if(temp == auteurSelectionnee)
            {
                a = new User(aut.getId(),aut.getNom(),aut.getPrenom(),aut.getEmail());
                System.out.println(aut);
            }
                
        }
         Promo p = null ;
        
         for (Promo pr : listP) {
            String temp = pr.getCode() ;
            if(temp == codeSelectionnee)
            {
                p = pr ;
                System.out.println(p);
            }
        nouveauLivre.setAuteur(a);
        nouveauLivre.setPromo(p);
        

        // Mettre à jour le livre dans la base de données
             ls.update(nouveauLivre);
             
             Alert al = new Alert(Alert.AlertType.INFORMATION);

            al.setTitle("Livre modifié");
            al.setHeaderText("");
            al.setContentText("Le livre " + nouveauLivre.getLibelle() + " a été modifié avec succès !");
            al.show();
            

             // Retourner à la vue de la liste des livres
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/ktebi/gui/Liste_livres.fxml"));
             Parent root = loader.load();
             Liste_LivresController controller = loader.getController();
             controller.updateListeLivres();
             
             Scene scene = new Scene(root);
             Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
             stage.setScene(scene);
             stage.show();
             
             
    

             
       
    }
         
    }

        
      
   
    public void setLivre(Livre livre) {
        this.livre = livre ;
        tfLibelle.setText(livre.getLibelle());
        taDescription.setText(livre.getDescription());
        tfEditeur.setText(livre.getEditeur());
        tfLangue.setText(livre.getLangue());
        tfCategorie.setText(livre.getCategorie());
        tfPrix.setText(String.valueOf(livre.getPrix()));
       
        
        auteurs.setValue(livre.getAuteur().getPrenom());
        
        promos.setValue(livre.getPromo().getCode());
        
        java.sql.Date sqlDate = livre.getDate_edition();
        LocalDate localDate = sqlDate.toLocalDate();
        
        date.setValue(localDate);

        
    }
    
    /*@FXML
    public void soumettre() throws SQLException {
        
}*/


    
}
