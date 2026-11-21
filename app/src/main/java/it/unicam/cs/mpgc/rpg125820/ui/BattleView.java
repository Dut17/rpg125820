package it.unicam.cs.mpgc.rpg125820.ui;

import it.unicam.cs.mpgc.rpg125820.battle.Game;
import it.unicam.cs.mpgc.rpg125820.battle.GameState;
import it.unicam.cs.mpgc.rpg125820.battle.HeroAction;
import it.unicam.cs.mpgc.rpg125820.battle.TurnResult;
import it.unicam.cs.mpgc.rpg125820.controller.GameController;
import it.unicam.cs.mpgc.rpg125820.model.Enemy;
import it.unicam.cs.mpgc.rpg125820.model.Hero;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.UncheckedIOException;
import java.util.Objects;

/**
 * Schermata di battaglia: statistiche, azioni, salvataggio e registro degli eventi.
 * Non contiene logica di gioco, la delega al {@link GameController}.
 */
public class BattleView extends VBox {

    private static final int SPACING = 10;
    private static final int PADDING = 16;
    private static final int LOG_ROWS = 10;

    private final GameController controller;
    private final Runnable onExit;
    private final Label heroLabel = new Label();
    private final Label enemyLabel = new Label();
    private final TextArea logArea = new TextArea();
    private final Button attackButton = new Button("Attacco");
    private final Button specialButton = new Button("Abilita speciale");
    private final Button potionButton = new Button("Pozione");
    private final Button saveButton = new Button("Salva");
    private final Button menuButton = new Button("Torna al menu");

    /**
     * @param onExit eseguito quando il giocatore torna al menu dopo la fine della partita
     */
    public BattleView(GameController controller, Runnable onExit) {
        super(SPACING);
        this.controller = Objects.requireNonNull(controller, "controller");
        this.onExit = Objects.requireNonNull(onExit, "onExit");
        build();
        refresh();
    }

    private void build() {
        setPadding(new Insets(PADDING));

        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefRowCount(LOG_ROWS);
        VBox.setVgrow(logArea, Priority.ALWAYS);

        attackButton.setOnAction(event -> play(HeroAction.ATTACK));
        specialButton.setOnAction(event -> play(HeroAction.SPECIAL));
        potionButton.setOnAction(event -> play(HeroAction.POTION));
        saveButton.setOnAction(event -> save());
        menuButton.setOnAction(event -> onExit.run());

        HBox actions = new HBox(SPACING, attackButton, specialButton, potionButton, saveButton);
        getChildren().addAll(heroLabel, enemyLabel, actions, logArea, menuButton);
    }

    private void play(HeroAction action) {
        Game game = controller.getGame();
        String enemyName = game.getCurrentEnemy().getName();
        int levelBefore = game.getHero().getLevel();
        int defeatedBefore = game.getDefeatedEnemies();
        try {
            TurnResult result = controller.play(action);
            log(describe(result, enemyName));
            if (game.getDefeatedEnemies() > defeatedBefore) {
                log(enemyName + " e stato sconfitto.");
            }
            if (game.getHero().getLevel() > levelBefore) {
                log("Sali al livello " + game.getHero().getLevel() + ": vita e mana ripristinati.");
            }
            if (game.getState() == GameState.VICTORY) {
                log("Hai completato l'arena.");
            } else if (game.getState() == GameState.DEFEAT) {
                log("Sei stato sconfitto.");
            } else if (result.damageTaken() > 0) {
                log(game.getCurrentEnemy().getName() + " ti colpisce: " + result.damageTaken() + " danni.");
            }
        } catch (IllegalStateException e) {
            log(e.getMessage());
        }
        refresh();
    }

    private static String describe(TurnResult result, String enemyName) {
        return switch (result.action()) {
            case ATTACK -> "Attacchi " + enemyName + ": " + result.damageDealt() + " danni.";
            case SPECIAL -> "Usi l'abilita speciale su " + enemyName + ": " + result.damageDealt() + " danni.";
            case POTION -> "Bevi una pozione: recuperi " + result.healed() + " vita.";
        };
    }

    private void save() {
        try {
            controller.saveGame();
            log("Partita salvata.");
        } catch (IllegalStateException | UncheckedIOException e) {
            log("Salvataggio non riuscito: " + e.getMessage());
        }
    }

    private void refresh() {
        Game game = controller.getGame();
        Hero hero = game.getHero();
        Enemy enemy = game.getCurrentEnemy();
        boolean inProgress = game.getState() == GameState.IN_PROGRESS;
        int enemyNumber = Math.min(game.getDefeatedEnemies() + 1, game.getTotalEnemies());

        heroLabel.setText(String.format("%s - livello %d - esperienza %d%nVita %d/%d - Mana %d - Pozioni %d",
                hero.getName(), hero.getLevel(), hero.getExperience(),
                hero.getHealth(), hero.getMaxHealth(), hero.getMana(), hero.getPotions()));
        enemyLabel.setText(String.format("Nemico %d/%d: %s - Vita %d/%d",
                enemyNumber, game.getTotalEnemies(), enemy.getName(), enemy.getHealth(), enemy.getMaxHealth()));

        attackButton.setDisable(!controller.canPerform(HeroAction.ATTACK));
        specialButton.setDisable(!controller.canPerform(HeroAction.SPECIAL));
        potionButton.setDisable(!controller.canPerform(HeroAction.POTION));
        saveButton.setDisable(!inProgress);
        menuButton.setVisible(!inProgress);
        menuButton.setManaged(!inProgress);
    }

    private void log(String message) {
        logArea.appendText(message + System.lineSeparator());
    }
}
