package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Abilita del Warrior: un colpo che infligge il doppio dell'attacco.
 */
public final class PowerStrike implements Ability {

    private static final String NAME = "Colpo potente";
    private static final int MANA_COST = 10;
    private static final int DAMAGE_MULTIPLIER = 2;

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
