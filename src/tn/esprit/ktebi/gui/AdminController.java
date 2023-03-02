/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

//import org.jfree.chart.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.esprit.ktebi.entities.Facture;
import tn.esprit.ktebi.services.AdminService;
import javafx.scene.chart.PieChart;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.ServiceFacture;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;

/**
 *
 * @author Pc Anis
 */
public class AdminController implements Initializable {

    private Label label;

    @FXML
    private Label cltSomme;

    AdminService ad = new AdminService();
    @FXML
    private Label livreSomme;
    @FXML
    private Label facSomme;

    @FXML
    private PieChart pieChart;

    ServiceFacture sf = new ServiceFacture();
//    @FXML
//    private BarChart<String, Number> barChart;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    public void Stat() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ServiceLignePanier liv = new ServiceLignePanier();
        ObservableList<Livre> list = (ObservableList) liv.listelivres();

        for (int i = 0; i < list.size(); ++i) {
            pieChartData.add(new PieChart.Data(((Livre) list.get(i)).getLibelle(), Double.parseDouble(String.valueOf(((Livre) list.get(i)).getPrix()))));
        }
        this.pieChart.setLegendVisible(false);
        this.pieChart.setData(pieChartData);
        this.pieChart.setPrefSize(650, 200); // Set the chart width to 600 and height to 400

    }

//    public void generateBarChart() throws SQLException {
//
//        List<Livre> livres = sf.listeLivresPlusAchetes();
//        CategoryAxis xAxis = new CategoryAxis();
//        NumberAxis yAxis = new NumberAxis();
//        this.barChart.setTitle("Les 10 livres les plus achetés");
//        xAxis.setLabel("Livre");
//        yAxis.setLabel("Nombre d'achats");
//
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        for (Livre livre : livres) {
//            XYChart.Data<String, Number> data = new XYChart.Data<>(livre.getLibelle(), sf.nombreAchats(livre));
//            Label label = new Label(livre.getLibelle());
//            label.setStyle("-fx-font-size: 10pt;");
//            data.getNode().setUserData(label);
//            series.getData().add(data);
//        }
//        this.barChart.getData().add(series);
//    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cltSomme.setText("Nombre des utilisateurs: " + String.valueOf(ad.getTotalUsers()));
        livreSomme.setText("Nombre des livres: " + String.valueOf(ad.getTotalLivres()));
        facSomme.setText("Nombre des factures: " + String.valueOf(ad.getTotalFactures()));

        try {
            //        sommecltt.setText("Nombre des clients: " + String.valueOf(ad.getTotalClient()));
//        sommelib.setText("Nombre des librairie: " + String.valueOf(ad.getTotalLib()));
//        sommeAut.setText("Nombre des auteurs: " + String.valueOf(ad.getTotalAuteurs()));
            this.Stat();
//            this.generateBarChart();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
