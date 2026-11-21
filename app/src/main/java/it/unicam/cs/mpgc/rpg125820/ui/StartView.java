package it.unicam.cs.mpgc.rpg125820.ui;

import it.unicam.cs.mpgc.rpg125820.controller.GameController;
import it.unicam.cs.mpgc.rpg125820.model.HeroType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.Objects;

/**
 * Schermata iniziale: nome, ruolo e scelta tra nuova partita e continua.
 * Non contiene logica di gioco, la delega al {@link GameController}.
 */
public class StartView extends VBox {

    private static final int SPACING = 12;
    private static final int PADDING = 24;
    private static final int FIELD_WIDTH = 240;

    private final GameController controller;
    private final Runnable onGameReady;
    private final TextField nameField = new TextField();
    private final ComboBox<HeroType> roleBox = new ComboBox<>();
    private final Label messageLabel = new Label();

    /**
     * @param onGameReady eseguito quando una partita e pronta, nuova o caricata
     */
    public StartView(GameController controller, Runnable onGameReady) {
        super(SPACING);
        this.controller = Objects.requireNonNull(controller, "controller");
        this.onGameReady = Objects.requireNonNull(onGameReady, "onGameReady");
        build();
    }

    private void build() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(PADDING));

        Label title = new Label("Cronache di Valdoria");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        nameField.setPromptText("Nome dell'eroe");
        nameField.setMaxWidth(FIELD_WIDTH);

        roleBox.getItems().addAll(HeroType.values());
        roleBox.setValue(HeroType.WARRIOR);
        roleBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(HeroType type) {
                return type == null ? "" : label(type);
            }

            @Override
            public HeroType fromString(String text) {
                return null;
            }
        });
        roleBox.setMaxWidth(FIELD_WIDTH);

        Button newGameButton = new Button("Nuova partita");
        newGameButton.setOnAction(event -> startNewGame());
        Button continueButton = new Button("Continua");
        continueButton.setOnAction(event -> continueGame());

        getChildren().addAll(title, nameField, roleBox, newGameButton, continueButton, messageLabel);
    }

    private void startNewGame() {
        try {
            controller.startNewGame(roleBox.getValue(), nameField.getText());
            onGameReady.run();
        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private void continueGame() {
        try {
            if (controller.loadGame()) {
                onGameReady.run();
            } else {
                messageLabel.setText("Nessun salvataggio trovato");
            }
        } catch (IllegalStateException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private static String label(HeroType type) {
        return switch (type) {
            case WARRIOR -> "Guerriero";
            case MAGE -> "Mago";
            case HUNTER -> "Cacciatore";
        };
    }
}
