package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Nemico controllato dal gioco. Le differenze tra nemici sono solo nei valori
 * passati al costruttore, quindi non servono sottoclassi.
 */
public class Enemy extends GameCharacter {

    public Enemy(String name, int maxHealth, int attackPower, int defense) {
        super(name, maxHealth, attackPower, defense);
    }
}
