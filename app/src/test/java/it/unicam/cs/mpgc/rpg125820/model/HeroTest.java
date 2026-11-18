package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private static class TestHero extends Hero {
        TestHero(int health, int attack, int defense) {
            super("Test", health, attack, defense);
        }
    }

    @Test
    void specialAbilityConsumesManaAndDoublesAttack() {
        TestHero hero = new TestHero(100, 10, 0);
        int rawDamage = hero.useSpecialAbility();
        assertEquals(20, rawDamage);
        assertEquals(10, hero.getMana());
    }

    @Test
    void specialAbilityFailsWhenManaIsNotEnough() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.useSpecialAbility();
        hero.useSpecialAbility();
        assertFalse(hero.canUseSpecialAbility());
        assertThrows(IllegalStateException.class, hero::useSpecialAbility);
    }

    @Test
    void potionHealsAndConsumesOnePotion() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.takeDamage(50);
        int healed = hero.usePotion();
        assertEquals(30, healed);
        assertEquals(80, hero.getHealth());
        assertEquals(2, hero.getPotions());
    }

    @Test
    void potionNeverHealsAboveMaxHealth() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.takeDamage(10);
        int healed = hero.usePotion();
        assertEquals(10, healed);
        assertEquals(100, hero.getHealth());
    }

    @Test
    void potionFailsWhenNoneAreLeft() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.usePotion();
        hero.usePotion();
        hero.usePotion();
        assertThrows(IllegalStateException.class, hero::usePotion);
    }
}
