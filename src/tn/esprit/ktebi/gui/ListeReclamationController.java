/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.net.URL;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceReclamation;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class ListeReclamationController implements Initializable {
    

    @FXML
    private TableView<Reclamation> Table;

    @FXML
    private TableColumn<Reclamation, LocalDate> colDate;
    
    @FXML
    private TableColumn<Reclamation, String> colContenu;    
    
    @FXML
    private TableColumn<Reclamation, String> ColEtat;

    @FXML
    private Button btnmod;

    @FXML
    private Button btnmod1;

    @FXML
    private TextField txtRec;
    
    ServiceReclamation sr = new ServiceReclamation();
    ObservableList<Reclamation> list;
    ReponseReclamationController rr = new ReponseReclamationController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficheRecById();
        Table.setOnMouseClicked(t->{
            if(t.getClickCount() ==1){
                Integer index = Table.getSelectionModel().getSelectedIndex();
                txtRec.setText(Table.getItems().get(index).getContenu());


            }
        }); 
       
    }
    
    
    
        public void AfficheRecById() {
        List<Reclamation> rec = new ArrayList<>();
        User user = new User(5);
        try {
            rec =sr.selectAllById(user.getId());

        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        list= FXCollections.observableList(rec);            
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        colContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        ColEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        Table.setItems(list);
        
}

    
    @FXML
    void ModifierReclamtion(ActionEvent event) {
        String etat ="en cours";
        ReponseReclamation rp = new ReponseReclamation(1);
        User u=new User(5);
        Integer index = Table.getSelectionModel().getSelectedIndex();
        Reclamation rec = new Reclamation(Table.getItems().get(index).getId(),
                txtRec.getText(),LocalDate.now(),etat,u,rp);
        try{
            sr.updateOne(rec);
            AfficheRecById();
            txtRec.setText("");

        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    void SupprimerReclamtion(ActionEvent event) {
        Integer index = Table.getSelectionModel().getSelectedIndex();
        Reclamation rec = new Reclamation(Table.getItems().get(index).getId());
        try{
            sr.deletOne(rec);           
            AfficheRecById();
            txtRec.setText("");


        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
