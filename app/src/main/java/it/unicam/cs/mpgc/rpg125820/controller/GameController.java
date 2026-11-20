package it.unicam.cs.mpgc.rpg125820.controller;

import it.unicam.cs.mpgc.rpg125820.battle.Game;
import it.unicam.cs.mpgc.rpg125820.battle.GameState;
import it.unicam.cs.mpgc.rpg125820.battle.HeroAction;
import it.unicam.cs.mpgc.rpg125820.battle.TurnResult;
import it.unicam.cs.mpgc.rpg125820.model.Arena;
import it.unicam.cs.mpgc.rpg125820.model.Hero;
import it.unicam.cs.mpgc.rpg125820.model.HeroType;
import it.unicam.cs.mpgc.rpg125820.persistence.LoadedGame;
import it.unicam.cs.mpgc.rpg125820.persistence.SaveService;

import java.util.Objects;
import java.util.Optional;

/**
 * Collega l'interfaccia grafica alla logica di gioco. Non usa JavaFX: una
 * qualsiasi altra interfaccia (web, mobile) puo riusarlo cosi com'e.
 * Tiene la partita corrente e il ruolo dell'eroe, necessario per salvare.
 */
public class GameController {

    private final SaveService saveService;
    private final Arena arena;
    private Game game;
    private HeroType heroType;

    public GameController(SaveService saveService, Arena arena) {
        this.saveService = Objects.requireNonNull(saveService, "saveService");
        this.arena = Objects.requireNonNull(arena, "arena");
    }

    /**
     * Inizia una nuova partita, sostituendo quella corrente.
     *
     * @throws IllegalArgumentException se il nome non e valido; in quel caso la partita corrente resta
     */
    public void startNewGame(HeroType heroType, String heroName) {
        Objects.requireNonNull(heroType, "heroType");
        Hero hero = heroType.create(heroName);
        this.game = new Game(hero, arena);
        this.heroType = heroType;
    }

    /**
     * Riprende l'ultima partita salvata, se esiste.
     *
     * @return vero se una partita e stata caricata, falso se non c'era nessun salvataggio
     * @throws IllegalStateException se il salvataggio non e valido; in quel caso la partita corrente resta
     */
    public boolean loadGame() {
        Optional<LoadedGame> loaded = saveService.load(arena);
        loaded.ifPresent(saved -> {
            this.game = saved.game();
            this.heroType = saved.heroType();
        });
        return loaded.isPresent();
    }

    /**
     * @throws IllegalStateException se non c'e una partita, o se e gia finita
     */
    public void saveGame() {
        saveService.save(requireGame(), heroType);
    }

    /**
     * @throws IllegalStateException se non c'e una partita, se e finita o se l'azione non e possibile
     */
    public TurnResult play(HeroAction action) {
        Objects.requireNonNull(action, "action");
        return requireGame().playTurn(action);
    }

    /**
     * Dice se l'azione e disponibile adesso, cosi la GUI puo disattivare i bottoni.
     */
    public boolean canPerform(HeroAction action) {
        Objects.requireNonNull(action, "action");
        if (game == null || game.getState() != GameState.IN_PROGRESS) {
            return false;
        }
        Hero hero = game.getHero();
        return switch (action) {
            case ATTACK -> true;
            case SPECIAL -> hero.canUseSpecialAbility();
            case POTION -> hero.getPotions() > 0;
        };
    }

    public boolean hasGame() {
        return game != null;
    }

    /**
     * @throws IllegalStateException se non c'e una partita
     */
    public Game getGame() {
        return requireGame();
    }

    /**
     * @throws IllegalStateException se non c'e una partita
     */
    public HeroType getHeroType() {
        requireGame();
        return heroType;
    }

    private Game requireGame() {
        if (game == null) {
            throw new IllegalStateException("Nessuna partita in corso");
        }
        return game;
    }
}
