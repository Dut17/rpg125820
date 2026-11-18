package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Personaggio controllato dal giocatore: ha mana, un'abilita speciale e pozioni.
 */
public abstract class Hero extends GameCharacter {

    private static final int MAX_MANA = 20;
    private static final int SPECIAL_ABILITY_COST = 10;
    private static final int SPECIAL_DAMAGE_MULTIPLIER = 2;
    private static final int POTION_HEAL_AMOUNT = 30;
    private static final int STARTING_POTIONS = 3;

    private int mana = MAX_MANA;
    private int potions = STARTING_POTIONS;

    protected Hero(String name, int maxHealth, int attackPower, int defense) {
        super(name, maxHealth, attackPower, defense);
    }

    public int getMana() {
        return mana;
    }

    public int getPotions() {
        return potions;
    }

    public boolean canUseSpecialAbility() {
        return mana >= SPECIAL_ABILITY_COST;
    }

    /**
     * Consuma il mana e calcola il danno grezzo dell'abilita speciale.
     *
     * @return il danno prima della difesa del bersaglio
     * @throws IllegalStateException se il mana non basta
     */
    public int useSpecialAbility() {
        if (!canUseSpecialAbility()) {
            throw new IllegalStateException("Mana insufficiente");
        }
        mana -= SPECIAL_ABILITY_COST;
        return getAttackPower() * SPECIAL_DAMAGE_MULTIPLIER;
    }

    /**
     * Beve una pozione di cura.
     *
     * @return la vita realmente recuperata
     * @throws IllegalStateException se non restano pozioni
     */
    public int usePotion() {
        if (potions == 0) {
            throw new IllegalStateException("Nessuna pozione rimasta");
        }
        potions--;
        return heal(POTION_HEAL_AMOUNT);
    }
}
