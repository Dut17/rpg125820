package it.unicam.cs.mpgc.rpg125820.ui;

import it.unicam.cs.mpgc.rpg125820.controller.GameController;
import it.unicam.cs.mpgc.rpg125820.model.Arenas;
import it.unicam.cs.mpgc.rpg125820.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg125820.persistence.SaveService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Punto di ingresso dell'interfaccia grafica. Crea il controller e mostra
 * la schermata iniziale. La schermata di battaglia verra aggiunta dopo.
 */
public class RpgApplication extends Application {

    private static final int WINDOW_WIDTH = 480;
    private static final int WINDOW_HEIGHT = 360;
    private static final Path SAVE_FILE = Path.of("saves", "partita.json");

    private GameController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.controller = new GameController(
                new SaveService(new JsonSaveRepository(SAVE_FILE)), Arenas.forest());
        stage.setTitle("RPG");
        stage.setScene(new Scene(new StartView(controller, this::showBattle), WINDOW_WIDTH, WINDOW_HEIGHT));
        stage.show();
    }

    private void showBattle() {
        Label label = new Label("Partita avviata: " + controller.getGame().getHero().getName());
        stage.getScene().setRoot(new StackPane(label));
    }
}
