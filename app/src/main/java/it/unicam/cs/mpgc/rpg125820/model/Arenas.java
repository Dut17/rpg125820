package it.unicam.cs.mpgc.rpg125820.model;

import java.util.List;

/**
 * Le arene disponibili nel gioco. Sono solo dati: per aggiungerne una basta
 * un nuovo metodo, senza toccare la logica di combattimento.
 */
public final class Arenas {

    private static final Arena FOREST = new Arena("Foresta", List.of(
            new EnemyTemplate("Goblin", 30, 8, 2, 10),
            new EnemyTemplate("Orco", 50, 12, 3, 20),
            new EnemyTemplate("Troll", 80, 16, 4, 40)));

    private Arenas() {
    }

    /**
     * @return l'arena Foresta: Goblin, Orco e il boss Troll, in questo ordine
     */
    public static Arena forest() {
        return FOREST;
    }
}
