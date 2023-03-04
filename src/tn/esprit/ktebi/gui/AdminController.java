/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import tn.esprit.ktebi.services.AdminService;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.services.ServiceFacture;
import tn.esprit.ktebi.services.ServiceLignePanier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Pc Anis
 */
public class AdminController implements Initializable {

    private Label label;

    AdminService ad = new AdminService();

    @FXML
    private PieChart pieChart;

    ServiceFacture sf = new ServiceFacture();
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Label cltSomme;
    @FXML
    private Label livreSomme;
    @FXML
    private Label facSomme;

    public void Stat() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        ServiceLignePanier liv = new ServiceLignePanier();
        ObservableList<Livre> list = (ObservableList) liv.listelivres();
        System.err.println(list.size());

        for (int i = 0; i < list.size(); ++i) {
            pieChartData.add(new PieChart.Data(((Livre) list.get(i)).getLibelle(), Double.parseDouble(String.valueOf(((Livre) list.get(i)).getPrix()))));
        }
        pieChart.setLabelLineLength(20);
        pieChart.setLegendVisible(false);
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(true);
        pieChart.setPrefSize(590, 170);

    }

    public void generateBarChart() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Livre> livres = sf.listeLivresPlusAchetes();

        for (Livre livre : livres) {
            dataset.setValue(sf.nombreAchats(livre), "Nombre d'achats", livre.getLibelle());
        }

        JFreeChart chart = ChartFactory.createBarChart("Les 10 livres les plus achetés", "Livre", "Nombre d'achats", dataset);

        javafx.scene.chart.Axis<String> xAxis = barChart.getXAxis();
        javafx.scene.chart.Axis<Number> yAxis = barChart.getYAxis();
        xAxis.setLabel("Livre");
        xAxis.setTickLabelsVisible(false);
        barChart.getXAxis().setTickLabelRotation(90);
        yAxis.setLabel("Nombre d'achats");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Object columnKey : dataset.getColumnKeys()) {
            String label = (String) columnKey;
            XYChart.Data<String, Number> data = new XYChart.Data<>(label, dataset.getValue("Nombre d'achats", (Comparable<?>) columnKey));
            // Set the label for the data
            data.setNode(new HoveredThresholdNode((int) (double) dataset.getValue("Nombre d'achats", (Comparable<?>) columnKey), label));
            series.getData().add(data);
        }

        barChart.getData().add(series);

        barChart.setAnimated(true);
        barChart.setBarGap(0);
        barChart.setCategoryGap(10);
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);
        barChart.setLegendSide(Side.RIGHT.RIGHT);

    }

    @FXML
    private void handleLabelFactures(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Factures-admin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//            showListeFactures();
        try {

            cltSomme.setText("Nombre des utilisateurs: " + String.valueOf(ad.getTotalUsers()));
            livreSomme.setText("Nombre des livres: " + String.valueOf(ad.getTotalLivres()));
            facSomme.setText("Nombre des factures: " + String.valueOf(ad.getTotalFactures()));
            this.Stat();
            this.generateBarChart();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class HoveredThresholdNode extends StackPane {

    HoveredThresholdNode(int value, String label) {
        setPrefSize(15, 15);
        final Label valueLabel = new Label(String.valueOf(value));
        valueLabel.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        valueLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        valueLabel.setVisible(false);
        final Label dataLabel = new Label(label);
        dataLabel.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        dataLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        dataLabel.setVisible(false);
        setOnMouseEntered(mouseEvent -> {
            valueLabel.setVisible(true);
            dataLabel.setVisible(true);
        });
        setOnMouseExited(mouseEvent -> {
            valueLabel.setVisible(false);
            dataLabel.setVisible(false);
        });
        getChildren().setAll(valueLabel, dataLabel);
    }
}
