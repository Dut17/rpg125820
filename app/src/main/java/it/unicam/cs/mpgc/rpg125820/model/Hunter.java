package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Eroe equilibrato: vita, attacco e difesa intermedi.
 */
public class Hunter extends Hero {

    private static final int MAX_HEALTH = 90;
    private static final int ATTACK_POWER = 15;
    private static final int DEFENSE = 3;

    public Hunter(String name) {
        super(name, MAX_HEALTH, ATTACK_POWER, DEFENSE, new DoubleShot());
    }
}
