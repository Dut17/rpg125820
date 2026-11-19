package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Abilita del Mage: una palla di fuoco che infligge il triplo dell'attacco.
 */
public final class Fireball implements Ability {

    private static final String NAME = "Palla di fuoco";
    private static final int MANA_COST = 15;
    private static final int DAMAGE_MULTIPLIER = 3;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getManaCost() {
        return MANA_COST;
    }

    @Override
    public int calculateDamage(int attackPower) {
        return attackPower * DAMAGE_MULTIPLIER;
    }
}
