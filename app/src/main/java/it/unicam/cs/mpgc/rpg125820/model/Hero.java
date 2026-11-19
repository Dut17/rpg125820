package it.unicam.cs.mpgc.rpg125820.model;

import java.util.Objects;

/**
 * Personaggio controllato dal giocatore: ha mana, un'abilita speciale e pozioni.
 * L'abilita speciale e delegata a un oggetto {@link Ability}, scelto da ogni ruolo.
 */
public abstract class Hero extends GameCharacter {

    private static final int MAX_MANA = 20;
    private static final int POTION_HEAL_AMOUNT = 30;
    private static final int STARTING_POTIONS = 3;

    private final Ability ability;
    private int mana = MAX_MANA;
    private int potions = STARTING_POTIONS;

    protected Hero(String name, int maxHealth, int attackPower, int defense, Ability ability) {
        super(name, maxHealth, attackPower, defense);
        this.ability = Objects.requireNonNull(ability, "ability");
    }

    public Ability getAbility() {
        return ability;
    }

    public int getMana() {
        return mana;
    }

    public int getPotions() {
        return potions;
    }

    public boolean canUseSpecialAbility() {
        return mana >= ability.getManaCost();
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
        mana -= ability.getManaCost();
        return ability.calculateDamage(getAttackPower());
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
