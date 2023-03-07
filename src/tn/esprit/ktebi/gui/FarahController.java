/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import com.jfoenix.controls.JFXComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class FarahController implements Initializable {

    @FXML
    private TextField txtLieu;
    @FXML
    private TextField txtPrix;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtNomEvent;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField imagefild;
    @FXML
    private TableView<?> tabevent;
    @FXML
    private TableColumn<?, ?> colname;
    @FXML
    private TableColumn<?, ?> coltheme;
    @FXML
    private TableColumn<?, ?> coldesc;
    @FXML
    private TableColumn<?, ?> collieu;
    @FXML
    private TableColumn<?, ?> datedebut;
    @FXML
    private TableColumn<?, ?> calid;
    @FXML
    private TableColumn<?, ?> colprix;
    @FXML
    private ImageView imageview;
    @FXML
    private Button btnupload;
    @FXML
    private Button bntmodif;
    @FXML
    private Button btnsupp;
    @FXML
    private Button btnreset;
    @FXML
    private JFXComboBox<?> combotheme;
    @FXML
    private Button ajouter;
    @FXML
    private TextField recherche;
    @FXML
    private Button btnRechercher;
    @FXML
    private Button tri;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SelectItems(MouseEvent event) {
    }

    @FXML
    private void imageupload(ActionEvent event) {
    }

    @FXML
    private void modifevent(ActionEvent event) {
    }

    @FXML
    private void deletehandler(ActionEvent event) {
    }

    @FXML
    private void ResetEvenet(ActionEvent event) {
    }

    @FXML
    private void ajouterrr(ActionEvent event) {
    }

    @FXML
    private void rechercher(ActionEvent event) {
    }

    @FXML
    private void tri(ActionEvent event) {
    }
    
}
