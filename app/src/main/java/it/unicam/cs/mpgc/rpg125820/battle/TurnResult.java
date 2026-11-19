package it.unicam.cs.mpgc.rpg125820.battle;

/**
 * Resoconto di un turno, pensato per essere mostrato dalla GUI.
 *
 * @param action      l'azione scelta dall'eroe
 * @param damageDealt il danno realmente inflitto al nemico (0 se l'eroe ha bevuto)
 * @param healed      la vita recuperata dall'eroe (0 se non ha bevuto)
 * @param damageTaken il danno subito dall'eroe nel contrattacco (0 se il nemico e morto)
 */
public record TurnResult(HeroAction action, int damageDealt, int healed, int damageTaken) {
}
