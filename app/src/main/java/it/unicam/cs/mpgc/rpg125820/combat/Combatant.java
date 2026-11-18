package it.unicam.cs.mpgc.rpg125820.combat;

/**
 * Qualsiasi entita che puo partecipare a un combattimento a turni.
 */
public interface Combatant {

    String getName();

    int getHealth();

    boolean isAlive();

    int attackPower();

    /**
     * Applica un danno al combattente.
     *
     * @param rawDamage il danno prima della difesa, non negativo
     * @return il danno realmente subito
     * @throws IllegalArgumentException se rawDamage e negativo
     */
    int takeDamage(int rawDamage);
}
