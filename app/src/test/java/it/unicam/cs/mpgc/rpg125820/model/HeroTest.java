package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private static class TestHero extends Hero {
        TestHero(int health, int attack, int defense) {
            super("Test", health, attack, defense, new PowerStrike());
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
    void warriorUsesPowerStrike() {
        Warrior warrior = new Warrior("Guerriero");
        assertEquals(24, warrior.useSpecialAbility());
        assertEquals(10, warrior.getMana());
    }

    @Test
    void mageUsesFireballAndCanCastOnlyOnce() {
        Mage mage = new Mage("Mago");
        assertEquals(54, mage.useSpecialAbility());
        assertEquals(5, mage.getMana());
        assertFalse(mage.canUseSpecialAbility());
    }

    @Test
    void hunterUsesDoubleShot() {
        Hunter hunter = new Hunter("Cacciatore");
        assertEquals(35, hunter.useSpecialAbility());
        assertEquals(12, hunter.getMana());
    }

    @Test
    void heroRequiresAnAbility() {
        assertThrows(NullPointerException.class, () -> new Hero("Test", 100, 10, 0, null) { });
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

    @Test
    void newHeroStartsAtLevelOneWithoutExperience() {
        TestHero hero = new TestHero(100, 10, 0);
        assertEquals(1, hero.getLevel());
        assertEquals(0, hero.getExperience());
    }

    @Test
    void experienceBelowThresholdKeepsLevelAndDoesNotRestore() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.takeDamage(50);
        hero.useSpecialAbility();
        int levelsGained = hero.gainExperience(19);
        assertEquals(0, levelsGained);
        assertEquals(1, hero.getLevel());
        assertEquals(19, hero.getExperience());
        assertEquals(50, hero.getHealth());
        assertEquals(10, hero.getMana());
    }

    @Test
    void reachingThresholdLevelsUpAndRestoresHealthAndMana() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.takeDamage(50);
        hero.useSpecialAbility();
        int levelsGained = hero.gainExperience(20);
        assertEquals(1, levelsGained);
        assertEquals(2, hero.getLevel());
        assertEquals(100, hero.getHealth());
        assertEquals(20, hero.getMana());
    }

    @Test
    void singleGainCanGiveSeveralLevels() {
        TestHero hero = new TestHero(100, 10, 0);
        int levelsGained = hero.gainExperience(100);
        assertEquals(3, levelsGained);
        assertEquals(4, hero.getLevel());
    }

    @Test
    void levelNeverExceedsMaximum() {
        TestHero hero = new TestHero(100, 10, 0);
        hero.gainExperience(1000);
        assertEquals(5, hero.getLevel());
        assertEquals(0, hero.gainExperience(50));
        assertEquals(1050, hero.getExperience());
    }

    @Test
    void negativeExperienceIsRejected() {
        TestHero hero = new TestHero(100, 10, 0);
        assertThrows(IllegalArgumentException.class, () -> hero.gainExperience(-1));
    }
}
