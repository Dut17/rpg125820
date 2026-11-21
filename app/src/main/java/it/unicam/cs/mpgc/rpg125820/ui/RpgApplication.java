package it.unicam.cs.mpgc.rpg125820.ui;

import it.unicam.cs.mpgc.rpg125820.controller.GameController;
import it.unicam.cs.mpgc.rpg125820.model.Arenas;
import it.unicam.cs.mpgc.rpg125820.persistence.JsonSaveRepository;
import it.unicam.cs.mpgc.rpg125820.persistence.SaveService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Punto di ingresso dell'interfaccia grafica. Crea il controller e passa
 * dalla schermata iniziale a quella di battaglia.
 */
public class RpgApplication extends Application {

    private static final int WINDOW_WIDTH = 520;
    private static final int WINDOW_HEIGHT = 480;
    private static final Path SAVE_FILE = Path.of("saves", "partita.json");

    private GameController controller;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        controller = new GameController(
                new SaveService(new JsonSaveRepository(SAVE_FILE)), Arenas.forest());
        scene = new Scene(new StartView(controller, this::showBattle), WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Cronache di Valdoria");
        stage.setScene(scene);
        stage.show();
    }

    private void showStart() {
        scene.setRoot(new StartView(controller, this::showBattle));
    }

    private void showBattle() {
        scene.setRoot(new BattleView(controller, this::showStart));
    }
}
