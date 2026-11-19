package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Abilita dell'Hunter: due colpi ravvicinati, doppio dell'attacco piu un bonus fisso.
 */
public final class DoubleShot implements Ability {

    private static final String NAME = "Doppio colpo";
    private static final int MANA_COST = 8;
    private static final int DAMAGE_MULTIPLIER = 2;
    private static final int BONUS_DAMAGE = 5;

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
        return attackPower * DAMAGE_MULTIPLIER + BONUS_DAMAGE;
    }
}
