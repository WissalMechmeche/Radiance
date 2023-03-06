/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.ktebi.entities.Role;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.UserService;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AdminFXMLController implements Initializable {
    @FXML
    private TableColumn<User, String> coladrs;

    @FXML
    private TableColumn<User, LocalDate> colddate;

    @FXML
    private TableColumn<User, String> colemail;

    @FXML
    private TableColumn<User, String> colnom;

    @FXML
    private TableColumn<User, String> colprenom;

    @FXML
    private TableColumn<User, String> colpsd;

    @FXML
    private TableColumn<String, String> colrole;

    @FXML
    private TableColumn<User, String> colstatus;

    @FXML
    private TableColumn<User, Integer> coltel;

    @FXML
    private TableView<User> table;
    
    @FXML
    private Button btndesc;

    @FXML
    private Button btnretour;
    
    UserService se = new UserService();
    List<User> user;
    ObservableList<User> list;

   
    @FXML
    private TextField txtRech;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user = new ArrayList<>();
        try {
            user =se.selectAll();
            System.out.println(user);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        /*String id = null;
           for(User u : user){
                id = u.getRole().getRole();
           }*/
        }      

       
        list= FXCollections.observableList(user);
        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        coladrs.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));        
        colddate.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colstatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.setItems(list);       
chercherReclamation();
    }
    void Afficher(){

    }

    @FXML
    void Desactiver(ActionEvent event) throws SQLException {
        Integer index = table.getSelectionModel().getSelectedIndex();
        User user = new User(table.getItems().get(index).getId());
        se.desactiver(user);
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("Acount");
        al.setHeaderText("Desactivation");
        al.setContentText("Compte desactivÃ©");
        al.show();

    }

    @FXML
    void btnretour(ActionEvent event) {

    } 
    void chercherReclamation(){

        //// Code Recherche
        FilteredList<User> listeFilter = new FilteredList<>(list, l-> true);
        txtRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
            listeFilter.setPredicate(user-> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCase = newValue.toLowerCase();
                if (user.getStatus().toLowerCase().contains(lowerCase)) {
                    return true;
                }else
                    
                    return false;
            });
        });
        SortedList<User> sortedData = new SortedList<>(listeFilter);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
}