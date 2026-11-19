package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Eroe fragile ma pericoloso: poca vita e difesa bassa, attacco alto.
 */
public class Mage extends Hero {

    private static final int MAX_HEALTH = 70;
    private static final int ATTACK_POWER = 18;
    private static final int DEFENSE = 1;

    public Mage(String name) {
        super(name, MAX_HEALTH, ATTACK_POWER, DEFENSE, new Fireball());
    }
}
