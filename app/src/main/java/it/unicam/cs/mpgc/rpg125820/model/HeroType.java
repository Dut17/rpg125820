package it.unicam.cs.mpgc.rpg125820.model;

import java.util.function.Function;

/**
 * Ruoli tra cui il giocatore puo scegliere. Ogni valore sa creare il proprio eroe,
 * cosi la GUI e il salvataggio non devono conoscere le singole classi.
 */
public enum HeroType {

    WARRIOR(Warrior::new),
    MAGE(Mage::new),
    HUNTER(Hunter::new);

    private final Function<String, Hero> factory;

    HeroType(Function<String, Hero> factory) {
        this.factory = factory;
    }

    public Hero create(String name) {
        return factory.apply(name);
    }
}
