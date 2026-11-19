package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Eroe robusto: molta vita e difesa alta, attacco medio.
 */
public class Warrior extends Hero {

    private static final int MAX_HEALTH = 120;
    private static final int ATTACK_POWER = 12;
    private static final int DEFENSE = 6;

    public Warrior(String name) {
        super(name, MAX_HEALTH, ATTACK_POWER, DEFENSE, new PowerStrike());
    }
}
