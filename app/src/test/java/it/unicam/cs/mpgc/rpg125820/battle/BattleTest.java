package it.unicam.cs.mpgc.rpg125820.battle;

import it.unicam.cs.mpgc.rpg125820.model.Enemy;
import it.unicam.cs.mpgc.rpg125820.model.Mage;
import it.unicam.cs.mpgc.rpg125820.model.Warrior;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void attackHurtsEnemyAndEnemyCounterattacks() {
        Warrior hero = new Warrior("Eroe");
        Enemy enemy = new Enemy("Goblin", 50, 10, 2);
        Battle battle = new Battle(hero, enemy);

        TurnResult result = battle.playTurn(HeroAction.ATTACK);

        assertEquals(new TurnResult(HeroAction.ATTACK, 10, 0, 4), result);
        assertEquals(40, enemy.getHealth());
        assertEquals(116, hero.getHealth());
    }

    @Test
    void specialAbilityConsumesManaAndDealsAbilityDamage() {
        Warrior hero = new Warrior("Eroe");
        Enemy enemy = new Enemy("Goblin", 50, 10, 2);
        Battle battle = new Battle(hero, enemy);

        TurnResult result = battle.playTurn(HeroAction.SPECIAL);

        assertEquals(new TurnResult(HeroAction.SPECIAL, 22, 0, 4), result);
        assertEquals(28, enemy.getHealth());
        assertEquals(10, hero.getMana());
    }

    @Test
    void potionHealsAndEnemyStillCounterattacks() {
        Warrior hero = new Warrior("Eroe");
        Enemy enemy = new Enemy("Goblin", 50, 10, 2);
        hero.takeDamage(50);
        Battle battle = new Battle(hero, enemy);

        TurnResult result = battle.playTurn(HeroAction.POTION);

        assertEquals(new TurnResult(HeroAction.POTION, 0, 30, 4), result);
        assertEquals(50, enemy.getHealth());
        assertEquals(102, hero.getHealth());
        assertEquals(2, hero.getPotions());
    }

    @Test
    void deadEnemyDoesNotCounterattackAndHeroWins() {
        Warrior hero = new Warrior("Eroe");
        Enemy enemy = new Enemy("Goblin", 10, 10, 2);
        Battle battle = new Battle(hero, enemy);

        TurnResult result = battle.playTurn(HeroAction.ATTACK);

        assertEquals(0, result.damageTaken());
        assertEquals(120, hero.getHealth());
        assertTrue(battle.isOver());
        assertTrue(battle.isHeroWinner());
    }

    @Test
    void deadHeroEndsBattleWithoutVictory() {
        Mage hero = new Mage("Maga");
        Enemy enemy = new Enemy("Drago", 500, 100, 0);
        Battle battle = new Battle(hero, enemy);

        TurnResult result = battle.playTurn(HeroAction.ATTACK);

        assertEquals(99, result.damageTaken());
        assertFalse(hero.isAlive());
        assertTrue(battle.isOver());
        assertFalse(battle.isHeroWinner());
    }

    @Test
    void newBattleIsNotOver() {
        Battle battle = new Battle(new Warrior("Eroe"), new Enemy("Goblin", 50, 10, 2));
        assertFalse(battle.isOver());
    }

    @Test
    void playingAfterTheEndFails() {
        Battle battle = new Battle(new Warrior("Eroe"), new Enemy("Goblin", 10, 10, 2));
        battle.playTurn(HeroAction.ATTACK);
        assertThrows(IllegalStateException.class, () -> battle.playTurn(HeroAction.ATTACK));
    }

    @Test
    void specialWithoutManaFailsAndNobodyIsHurt() {
        Warrior hero = new Warrior("Eroe");
        Enemy enemy = new Enemy("Golem", 1000, 1, 0);
        Battle battle = new Battle(hero, enemy);
        battle.playTurn(HeroAction.SPECIAL);
        battle.playTurn(HeroAction.SPECIAL);
        int heroHealth = hero.getHealth();
        int enemyHealth = enemy.getHealth();

        assertThrows(IllegalStateException.class, () -> battle.playTurn(HeroAction.SPECIAL));

        assertEquals(heroHealth, hero.getHealth());
        assertEquals(enemyHealth, enemy.getHealth());
    }

    @Test
    void potionWithoutPotionsLeftFails() {
        Battle battle = new Battle(new Warrior("Eroe"), new Enemy("Golem", 1000, 1, 0));
        battle.playTurn(HeroAction.POTION);
        battle.playTurn(HeroAction.POTION);
        battle.playTurn(HeroAction.POTION);
        assertThrows(IllegalStateException.class, () -> battle.playTurn(HeroAction.POTION));
    }

    @Test
    void constructorRejectsNullArguments() {
        Warrior hero = new Warrior("Eroe");
        Enemy enemy = new Enemy("Goblin", 50, 10, 2);
        assertThrows(NullPointerException.class, () -> new Battle(null, enemy));
        assertThrows(NullPointerException.class, () -> new Battle(hero, null));
    }
}
