package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArenaTest {

    private static final EnemyTemplate GOBLIN = new EnemyTemplate("Goblin", 40, 8, 2, 10);
    private static final EnemyTemplate ORC = new EnemyTemplate("Orco", 60, 12, 4, 20);

    @Test
    void enemiesAreCreatedInOrder() {
        Arena arena = new Arena("Foresta", List.of(GOBLIN, ORC));
        List<Enemy> enemies = arena.createEnemies();
        assertEquals(2, enemies.size());
        assertEquals("Goblin", enemies.get(0).getName());
        assertEquals("Orco", enemies.get(1).getName());
    }

    @Test
    void eachCallCreatesFreshEnemies() {
        Arena arena = new Arena("Foresta", List.of(GOBLIN));
        Enemy first = arena.createEnemies().get(0);
        first.takeDamage(1000);
        Enemy second = arena.createEnemies().get(0);
        assertFalse(first.isAlive());
        assertTrue(second.isAlive());
    }

    @Test
    void createdEnemiesCarryTheTemplateExperienceReward() {
        Arena arena = new Arena("Foresta", List.of(GOBLIN, ORC));
        List<Enemy> enemies = arena.createEnemies();
        assertEquals(10, enemies.get(0).getExperienceReward());
        assertEquals(20, enemies.get(1).getExperienceReward());
    }

    @Test
    void emptyNameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Arena(" ", List.of(GOBLIN)));
    }

    @Test
    void emptyEnemyListIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Arena("Foresta", List.of()));
    }
}
