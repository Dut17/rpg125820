package it.unicam.cs.mpgc.rpg125820.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Punto di ingresso dell'interfaccia grafica. Per ora mostra solo una finestra
 * di prova, per verificare che JavaFX funzioni con il build.
 */
public class RpgApplication extends Application {

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 200;

    @Override
    public void start(Stage stage) {
        Label label = new Label("RPG - finestra di prova");
        stage.setTitle("RPG");
        stage.setScene(new Scene(new StackPane(label), WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();
    }
}
