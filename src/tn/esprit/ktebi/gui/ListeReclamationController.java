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
import java.sql.SQLDataException;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.ktebi.entities.Reclamation;
import tn.esprit.ktebi.entities.ReponseReclamation;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceReclamation;
import tn.esprit.ktebi.services.ServiceReponse;

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
    private TableColumn colimg;
    @FXML
    private Button btnmod;

    @FXML
    private Button btnsupp;
    
    @FXML
    private Button btnretour;
    
    @FXML
    private Button btnrep;

    @FXML
    private TextArea txtRec;
    
    @FXML
    private ImageView imgview;
     @FXML
    private TextField txtRech;
    static  Integer index;
    static  Integer id_rep;       
    ServiceReponse srep = new ServiceReponse();

    InputStream in;    
    ServiceReclamation sr = new ServiceReclamation();
    ObservableList<Reclamation> list;
    ReponseReclamationController rr = new ReponseReclamationController();
    List<Reclamation> rec = new ArrayList<>();          

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
                Table.setOnMouseClicked(t->{
            if(t.getClickCount() ==1){
                 index = Table.getSelectionModel().getSelectedIndex();
                txtRec.setText(Table.getItems().get(index).getContenu());
            }
        });    
            
        try {
            AfficheRecById();
        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtRec.setWrapText(true);
       chercherReclamation();
    }
    
    
    
        public void AfficheRecById() throws SQLException {
        rec = new ArrayList<>();
        User user = new User(1);
        try {
            rec =sr.selectAllById(user.getId());

        } catch (SQLException ex) {
            Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        list= FXCollections.observableList(rec);            
        colDate.setCellValueFactory(new PropertyValueFactory<>("date_reclamation"));
        colContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        ColEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactory =(e)->{
            final TableCell<Reclamation, String> cell=new TableCell<Reclamation, String>(){
              @Override
              public void updateItem(String item,boolean empty){
                  super.updateItem(item,empty);
                  if(empty){
                      setGraphic(null);
                      setText(null);
                  }else{
                      
                      final Button edit = new Button("show");
                      edit.setOnAction(event ->{
                          Reclamation r = getTableView().getItems().get(getIndex());
                          ReponseReclamation liste = new ReponseReclamation();
    
                          try {
                              liste=srep.selectAllById(r.getId());
                          } catch (SQLException ex) {
                              Logger.getLogger(ListeReclamationController.class.getName()).log(Level.SEVERE, null, ex);
                          }  
                            Alert al = new Alert(Alert.AlertType.INFORMATION);                           
                            if(!al.isShowing()){
                            al.setTitle("Reponse");
                            al.setHeaderText("Votre reclamation est : \n"+r.getContenu());    
                            al.setContentText("Date Reponse :\n"+liste.getDateRep()+"\n"+
                                              "Reponse :\n"+liste.getContenu());
                            al.show();
                            }else{
                                al.hide();
                            }

                            
                      });
                      setGraphic(edit);
                      setText(null);
                  };
              };
            };      
            return cell;
};
        colimg.setCellFactory(cellFactory);
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
        User u=new User(1);
         index = Table.getSelectionModel().getSelectedIndex();
         id_rep =Table.getItems().get(index).getId(); 
        Reclamation rec = new Reclamation(id_rep,
                txtRec.getText(),LocalDate.now(),etat,u);
        try{
        sr.updateOne(rec);
        resetTableData();
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
   
        void chercherReclamation(){

             //// Code Recherche
             try {
            rec=sr.selectAll();     
            list = FXCollections.observableArrayList(rec);     
            Table.setItems(list);
            FilteredList<Reclamation> listeFilter = new FilteredList<>(list, l-> true);
               txtRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    listeFilter.setPredicate(reclamation-> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lowerCase = newValue.toLowerCase();
                        if (reclamation.getEtat().toLowerCase().contains(lowerCase)) {
                            return true;
                        }else

                        return false;
                    });
                });               
                SortedList<Reclamation> sortedData = new SortedList<>(listeFilter);
                sortedData.comparatorProperty().bind(Table.comparatorProperty());
                Table.setItems(sortedData);
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        
    @FXML
    void Reponses(ActionEvent event) throws IOException {
        Parent retour = FXMLLoader.load(getClass().getResource("UserReponseReclamation.fxml"));
        Scene scene = new Scene(retour);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Ajouter une Reclamations");

        stage.setScene(scene);
        stage.show();
        System.out.println(id_rep);

    }
    
  public void resetTableData() throws SQLDataException, SQLException {

        rec = new ArrayList<>();
        User user=new User(1);
        rec =sr.selectAllById(user.getId());
        ObservableList<Reclamation> data = FXCollections.observableArrayList(rec);
        Table.setItems(data);
    }    
}
