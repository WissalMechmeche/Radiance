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
    private void ModifierLivre(ActionEvent event) throws SQLException {
        
       
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
        String libelle = tfLibelle.getText();
        
        String description = taDescription.getText();
        
        LocalDate dateEdition = date.getValue();
       
        String auteur = auteurs.getValue();
        
        String promo = promos.getValue();
        
        float prix = Float.parseFloat(tfPrix.getText());
        
        String editeur = tfEditeur.getText();

        Livre livre = new Livre();
        
        livre.setId(id);
        
        livre.setLibelle(libelle);
        livre.setDescription(description);
        livre.setEditeur(editeur);
        livre.setPrix(prix);
        livre.setDate_edition(Date.valueOf(dateEdition));
        
        String auteurSelectionnee = auteurs.getValue();
        
        User a = null ;
        for (User aut : listU) {
            String temp = aut.getPrenom() ;
            if(temp == auteurSelectionnee)
            {
                a = new User(aut.getId(),aut.getNom(),aut.getPrenom());
                System.out.println(aut);
            }
                
        }
         Promo pr = null ;
        
        String codeSelectionnee = promos.getValue();
        
         for (Promo pro : listP) {
            String temp = pro.getCode() ;
            if(temp == codeSelectionnee)
            {
                pr = pro ;
                System.out.println(pr);
            }
                
        }
        livre.setAuteur(a);
        livre.setPromo(pr);

        LivreServicee ls = new LivreServicee();
        ls.update(livre);
        
        System.out.println(livre);
        
    }

        int id =0;
      
    public void setLivre(Livre livre) {
        id = livre.getId();
        tfLibelle.setText(livre.getLibelle());
        taDescription.setText(livre.getDescription());
        tfEditeur.setText(livre.getEditeur());
        tfLangue.setText(livre.getLangue());
        tfCategorie.setText(livre.getCategorie());
        tfPrix.setText(String.valueOf(livre.getPrix()));
        auteurs.getSelectionModel().select(livre.getAuteur().getPrenom());
        promos.getSelectionModel().select(livre.getPromo().getCode());
        
        java.sql.Date sqlDate = livre.getDate_edition();
        LocalDate localDate = sqlDate.toLocalDate();
        
        date.setValue(localDate);

        
    }
    
    @FXML
    public void isSoumettre(ActionEvent event) throws SQLException {
        // Vérification des champs obligatoires
        if (tfLibelle.getText().isEmpty() || auteurs.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setContentText("Veuillez remplir tous les champs obligatoires (*)");
            alert.showAndWait();
            return;
        }

        // Récupération des valeurs des champs
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
                aut = new User(auteur.getId(),auteur.getNom(),auteur.getPrenom());
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
         
          
        try {
            prix = Float.parseFloat(tfPrix.getText());
            if (prix < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Valeur invalide");
            alert.setContentText("Le prix doit être un nombre positif.");
            alert.showAndWait();
            return;
        }

        // Création du livre
        Livre livre = new Livre(libelle, description,editeur,new java.sql.Date(Date.valueOf(dateEdition).getTime()),categorie,prix,langue,null,pr, aut);

        // Ajout du livre dans la base de données
        ls.create(livre);

        // Redirection vers la vue de la liste des livres
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/ktebi/gui/Liste_livres.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Liste_LivresController controller = loader.getController();

        // Mettre à jour la liste des livres
        controller.updateListeLivres();

        // Retourner à la vue de la liste des livres
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public Livre getLivre() {
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
                aut = new User(auteur.getId(),auteur.getNom(),auteur.getPrenom());
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

        Livre livre = new Livre(libelle, description,editeur,new java.sql.Date(Date.valueOf(dateEdition).getTime()),categorie,prix,langue,null,pr, aut);
        livre.setId(this.livre.getId()); // conserver l'ID du livre original
        return livre;
    }


    
}
