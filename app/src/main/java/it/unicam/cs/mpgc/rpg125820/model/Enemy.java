package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Nemico controllato dal gioco. Le differenze tra nemici sono solo nei valori
 * passati al costruttore, quindi non servono sottoclassi.
 */
public class Enemy extends GameCharacter {

    private final int experienceReward;

    public Enemy(String name, int maxHealth, int attackPower, int defense, int experienceReward) {
        super(name, maxHealth, attackPower, defense);
        if (experienceReward < 0) {
            throw new IllegalArgumentException("Il premio di esperienza non puo essere negativo");
        }
        this.experienceReward = experienceReward;
    }

    public Enemy(String name, int maxHealth, int attackPower, int defense) {
        this(name, maxHealth, attackPower, defense, 0);
    }

    public int getExperienceReward() {
        return experienceReward;
    }
}
