package it.unicam.cs.mpgc.rpg125820.persistence;

import it.unicam.cs.mpgc.rpg125820.model.HeroType;

import java.util.Objects;

/**
 * Dati minimi per riprendere una partita. Contiene solo valori, nessuna logica di gioco.
 */
public record GameSnapshot(HeroType heroType, String heroName, int level, int experience,
                           int health, int mana, int potions, int defeatedEnemies) {

    public GameSnapshot {
        Objects.requireNonNull(heroType, "heroType");
        if (heroName == null || heroName.isBlank()) {
            throw new IllegalArgumentException("Il nome dell'eroe non puo essere vuoto");
        }
        if (level < 1) {
            throw new IllegalArgumentException("Il livello parte da 1");
        }
        if (experience < 0 || health < 0 || mana < 0 || potions < 0 || defeatedEnemies < 0) {
            throw new IllegalArgumentException("I valori numerici non possono essere negativi");
        }
    }
}
