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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceReclamation;

/**
 * FXML Controller class
 *
 * @author Dell 6540
 */
public class ReponseReclamationController implements Initializable {

    @FXML
    private TableView<Reclamation> Table;    
    
    @FXML
    private TableColumn<Reclamation, LocalDate> colDate;
    
    @FXML
    private TableColumn<Reclamation, String> colContenu;    
    
    @FXML
    private TableColumn<Reclamation, String> ColId;

    @FXML
    private Button btnmod;

    @FXML
    private TextField txtRec;

    @FXML
    private TextField txtRep;
    
    @FXML
    private TextField id_rec;
    
        ServiceReclamation sr = new ServiceReclamation();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficheRec();
                Table.setOnMouseClicked(t->{
            if(t.getClickCount() ==1){
                Integer index = Table.getSelectionModel().getSelectedIndex();
                txtRec.setText(Table.getItems().get(index).getContenu());
                id_rec.setText(String.valueOf(Table.getItems().get(index).getId()));

            }
        });
    } 
    public void AfficheRec() {
        List<Reclamation> rec = new ArrayList<>();
        try {
            rec =sr.selectAll();

        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<Reclamation> list= FXCollections.observableList(rec);            
        colDate.setCellValueFactory(new PropertyValueFactory<Reclamation, LocalDate>("date_reclamation"));
        colContenu.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("contenu"));
        ColId.setCellValueFactory(new PropertyValueFactory<Reclamation, String>("id"));
        Table.setItems(list);       
}    
     @FXML
    void ReponseReclamtion(ActionEvent event) throws IOException {
        if (txtRep.getText().equals("")) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Controle de saisie");
            al.setHeaderText("Erreur de saisie !");
            al.setContentText("Le contenu est vide !");
            al.show();
        }else{
            Reclamation rec = new Reclamation(Integer.parseInt(id_rec.getText()));
            ReponseReclamation rep = new ReponseReclamation(txtRep.getText(),rec);
            
            ServiceReclamation sr = new ServiceReclamation();
            try {
                sr.createOneReponse(rep);
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Succés");
                al.setHeaderText("Reponse envoyé");
                al.show();
                AfficheRec();
                txtRep.setText("");
                id_rec.setText("id");
                txtRec.setText("");
            } catch (SQLException ex) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur");
                al.setHeaderText("Erreur Interne");
                al.setContentText(ex.getMessage());
                al.show();
            }
            
        }
    }   
}
