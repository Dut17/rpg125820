package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyTest {

    @Test
    void enemyStartsWithFullHealth() {
        Enemy goblin = new Enemy("Goblin", 40, 8, 2);
        assertEquals("Goblin", goblin.getName());
        assertEquals(40, goblin.getHealth());
        assertEquals(8, goblin.getAttackPower());
        assertTrue(goblin.isAlive());
    }

    @Test
    void enemyTakesDamageReducedByDefense() {
        Enemy goblin = new Enemy("Goblin", 40, 8, 2);
        assertEquals(8, goblin.takeDamage(10));
        assertEquals(32, goblin.getHealth());
    }

    @Test
    void enemyDiesWhenHealthReachesZero() {
        Enemy goblin = new Enemy("Goblin", 10, 8, 0);
        goblin.takeDamage(50);
        assertFalse(goblin.isAlive());
    }

    @Test
    void invalidStatsAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Enemy("Goblin", 0, 8, 2));
    }
}
