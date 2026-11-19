package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTypeTest {

    @Test
    void warriorTypeCreatesWarrior() {
        Hero hero = HeroType.WARRIOR.create("Aldo");
        assertInstanceOf(Warrior.class, hero);
        assertEquals("Aldo", hero.getName());
    }

    @Test
    void mageTypeCreatesMage() {
        Hero hero = HeroType.MAGE.create("Maga");
        assertInstanceOf(Mage.class, hero);
        assertEquals("Maga", hero.getName());
    }

    @Test
    void hunterTypeCreatesHunter() {
        Hero hero = HeroType.HUNTER.create("Gianni");
        assertInstanceOf(Hunter.class, hero);
        assertEquals("Gianni", hero.getName());
    }

    @Test
    void eachCallCreatesAFreshHero() {
        Hero first = HeroType.MAGE.create("Uno");
        Hero second = HeroType.MAGE.create("Due");
        first.useSpecialAbility();
        assertNotSame(first, second);
        assertEquals(5, first.getMana());
        assertEquals(20, second.getMana());
    }
}
