package it.unicam.cs.mpgc.rpg125820.model;

import java.util.List;

/**
 * Luogo di combattimento: ha un nome e la lista ordinata dei nemici da affrontare.
 * Le arene sono dati, non classi: ne esistono diverse istanze con nemici diversi.
 */
public class Arena {

    private final String name;
    private final List<EnemyTemplate> enemyTemplates;

    public Arena(String name, List<EnemyTemplate> enemyTemplates) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Il nome non puo essere vuoto");
        }
        if (enemyTemplates == null || enemyTemplates.isEmpty()) {
            throw new IllegalArgumentException("Serve almeno un nemico");
        }
        this.name = name;
        this.enemyTemplates = List.copyOf(enemyTemplates);
    }

    public String getName() {
        return name;
    }

    /**
     * @return le descrizioni dei nemici nell'ordine di combattimento, lista non modificabile
     */
    public List<EnemyTemplate> getEnemyTemplates() {
        return enemyTemplates;
    }

    /**
     * @return nemici nuovi con vita piena, nell'ordine in cui vanno affrontati
     */
    public List<Enemy> createEnemies() {
        return enemyTemplates.stream().map(EnemyTemplate::createEnemy).toList();
    }
}
