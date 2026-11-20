package it.unicam.cs.mpgc.rpg125820.model;

import java.util.Objects;

/**
 * Personaggio controllato dal giocatore: ha mana, un'abilita speciale, pozioni,
 * livello ed esperienza.
 * L'abilita speciale e delegata a un oggetto {@link Ability}, scelto da ogni ruolo.
 */
public abstract class Hero extends GameCharacter {

    private static final int MAX_MANA = 20;
    private static final int POTION_HEAL_AMOUNT = 30;
    private static final int STARTING_POTIONS = 3;
    private static final int MAX_LEVEL = 5;
    // indice = livello; valore = esperienza totale necessaria per raggiungerlo
    private static final int[] EXPERIENCE_FOR_LEVEL = {0, 0, 20, 50, 100, 160};

    private final Ability ability;
    private int mana = MAX_MANA;
    private int potions = STARTING_POTIONS;
    private int level = 1;
    private int experience = 0;

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

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
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

    /**
     * Aggiunge esperienza; a ogni livello guadagnato l'eroe recupera
     * tutta la vita e tutto il mana.
     *
     * @param amount l'esperienza guadagnata, non negativa
     * @return il numero di livelli guadagnati
     */
    public int gainExperience(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("L'esperienza non puo essere negativa");
        }
        experience += amount;
        int gained = 0;
        while (level < MAX_LEVEL && experience >= EXPERIENCE_FOR_LEVEL[level + 1]) {
            level++;
            gained++;
        }
        if (gained > 0) {
            heal(getMaxHealth());
            mana = MAX_MANA;
        }
        return gained;
    }
}
