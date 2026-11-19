package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Descrizione fissa di un tipo di nemico: nome e statistiche base.
 * Non ha stato di gioco, quindi puo essere riusata in ogni partita.
 */
public record EnemyTemplate(String name, int maxHealth, int attackPower, int defense) {

    /**
     * @return un nuovo nemico con vita piena
     */
    public Enemy createEnemy() {
        return new Enemy(name, maxHealth, attackPower, defense);
    }
}
