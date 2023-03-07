/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Pc Anis
 */
public class App extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private static Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomeEventFXML.fxml"));
        stage.setTitle("Ktebi App");
        stage.setScene(new Scene(root, Color.TRANSPARENT));

        // phase 2
        stage.initStyle(StageStyle.DECORATED);
        makeDraggable(root);

        stage.show();

        window = stage;
    }

    public static void main(String[] args) {
        launch();

    }

    public static Stage getWindow() {
        return window;
    }

    private void makeDraggable(Parent root) {
        root.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        root.setOnMouseDragged(e -> {
            window.setX(e.getScreenX() - xOffset);
            window.setY(e.getScreenY() - yOffset);
        });
    }

}
