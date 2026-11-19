package it.unicam.cs.mpgc.rpg125820.battle;

import it.unicam.cs.mpgc.rpg125820.model.Enemy;
import it.unicam.cs.mpgc.rpg125820.model.Hero;

import java.util.Objects;

/**
 * Scontro a turni tra un eroe e un nemico. Contiene solo la logica di gioco:
 * non legge input e non stampa nulla, cosi puo essere usata da qualsiasi interfaccia.
 */
public class Battle {

    private final Hero hero;
    private final Enemy enemy;

    public Battle(Hero hero, Enemy enemy) {
        this.hero = Objects.requireNonNull(hero, "hero");
        this.enemy = Objects.requireNonNull(enemy, "enemy");
    }

    public Hero getHero() {
        return hero;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public boolean isOver() {
        return !hero.isAlive() || !enemy.isAlive();
    }

    public boolean isHeroWinner() {
        return !enemy.isAlive();
    }

    /**
     * Gioca un turno: prima agisce l'eroe, poi contrattacca il nemico se e ancora vivo.
     *
     * @param action l'azione scelta dal giocatore
     * @return il resoconto del turno
     * @throws IllegalStateException se lo scontro e finito, o se l'azione non e possibile
     *                               (mana insufficiente o pozioni finite)
     */
    public TurnResult playTurn(HeroAction action) {
        Objects.requireNonNull(action, "action");
        if (isOver()) {
            throw new IllegalStateException("Lo scontro e gia finito");
        }
        int damageDealt = 0;
        int healed = 0;
        switch (action) {
            case ATTACK -> damageDealt = enemy.takeDamage(hero.getAttackPower());
            case SPECIAL -> damageDealt = enemy.takeDamage(hero.useSpecialAbility());
            case POTION -> healed = hero.usePotion();
        }
        int damageTaken = enemy.isAlive() ? hero.takeDamage(enemy.getAttackPower()) : 0;
        return new TurnResult(action, damageDealt, healed, damageTaken);
    }
}
