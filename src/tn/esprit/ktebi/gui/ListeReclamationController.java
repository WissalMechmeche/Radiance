/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.io.InputStream;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private TableColumn<Reclamation, Image> colimg;
    @FXML
    private Button btnmod;

    @FXML
    private Button btnmod1;
    
    @FXML
    private Button btnretour;

    @FXML
    private TextField txtRec;
    @FXML
    private ImageView imgview;

    InputStream in;    
    ServiceReclamation sr = new ServiceReclamation();
    ObservableList<Reclamation> list;
    ReponseReclamationController rr = new ReponseReclamationController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            AfficheRecById();
        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Table.setOnMouseClicked(t->{
            if(t.getClickCount() ==1){
                Integer index = Table.getSelectionModel().getSelectedIndex();
                txtRec.setText(Table.getItems().get(index).getContenu());


            }
        }); 
       
    }
    
    
    
        public void AfficheRecById() throws SQLException {
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
            /*in =list.get(0).getImg1().getBinaryStream();
            Image image = new Image(in);
            imgview.setFitWidth(200);
            imgview.setFitHeight(200);
            imgview.scaleXProperty();
            imgview.scaleYProperty();
            imgview.setSmooth(true);
            imgview.setCache(true);
            imgview.setImage(image);*/

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
    
    @FXML
    private void Retour(ActionEvent event) throws IOException {
        Parent retour = FXMLLoader.load(getClass().getResource("AjouterReclamation.fxml"));
        Scene scene = new Scene(retour);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Ajouter une Reclamations");
        stage.setScene(scene);
        stage.show();        
    }    
}
