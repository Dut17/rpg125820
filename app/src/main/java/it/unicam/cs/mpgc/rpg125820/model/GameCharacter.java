package it.unicam.cs.mpgc.rpg125820.model;

import it.unicam.cs.mpgc.rpg125820.combat.Combatant;

/**
 * Parte comune a eroi e nemici: nome, vita, attacco e difesa.
 */
public abstract class GameCharacter implements Combatant {

    private static final int MINIMUM_DAMAGE = 1;

    private final String name;
    private final int maxHealth;
    private final int attackPower;
    private final int defense;
    private int currentHealth;

    protected GameCharacter(String name, int maxHealth, int attackPower, int defense) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Il nome non puo essere vuoto");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("La vita massima deve essere positiva");
        }
        if (attackPower < 0 || defense < 0) {
            throw new IllegalArgumentException("Attacco e difesa non possono essere negativi");
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
        this.currentHealth = maxHealth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

    @Override
    public int getAttackPower() {
        return attackPower;
    }

    @Override
    public int takeDamage(int rawDamage) {
        if (rawDamage < 0) {
            throw new IllegalArgumentException("Il danno non puo essere negativo");
        }
        int appliedDamage = Math.max(MINIMUM_DAMAGE, rawDamage - defense);
        currentHealth = Math.max(0, currentHealth - appliedDamage);
        return appliedDamage;
    }

    /**
     * Ripristina vita senza superare il massimo.
     *
     * @param amount la vita da ripristinare, non negativa
     * @return la vita realmente recuperata
     */
    public int heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("La cura non puo essere negativa");
        }
        int healed = Math.min(amount, maxHealth - currentHealth);
        currentHealth += healed;
        return healed;
    }

    /**
     * Imposta la vita corrente, per ricaricare una partita salvata.
     *
     * @param health la vita da impostare, tra 1 e la vita massima
     */
    protected void restoreHealth(int health) {
        if (health <= 0 || health > maxHealth) {
            throw new IllegalArgumentException("La vita deve essere tra 1 e la vita massima");
        }
        currentHealth = health;
    }
}
