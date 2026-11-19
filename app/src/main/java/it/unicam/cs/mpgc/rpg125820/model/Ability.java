package it.unicam.cs.mpgc.rpg125820.model;

/**
 * Abilita speciale di un eroe: ha un nome, un costo in mana e una formula di danno.
 * Ogni ruolo ha la sua implementazione, cosi un nuovo ruolo non obbliga a toccare Hero.
 */
public interface Ability {

    String getName();

    int getManaCost();

    /**
     * @param attackPower la potenza di attacco di chi usa l'abilita
     * @return il danno prima della difesa del bersaglio
     */
    int calculateDamage(int attackPower);
}
