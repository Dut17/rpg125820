package it.unicam.cs.mpgc.rpg125820.persistence;

import it.unicam.cs.mpgc.rpg125820.battle.Game;
import it.unicam.cs.mpgc.rpg125820.model.HeroType;

import java.util.Objects;

/**
 * Partita ripresa da un salvataggio, insieme al ruolo dell'eroe: serve per poterla
 * salvare di nuovo, perche l'eroe da solo non sa dire che ruolo ha.
 */
public record LoadedGame(Game game, HeroType heroType) {

    public LoadedGame {
        Objects.requireNonNull(game, "game");
        Objects.requireNonNull(heroType, "heroType");
    }
}
