package it.unicam.cs.mpgc.rpg125820.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArenasTest {

    @Test
    void forestHasGoblinOrcAndTrollInOrder() {
        Arena forest = Arenas.forest();
        assertEquals("Foresta", forest.getName());
        List<String> names = forest.createEnemies().stream().map(Enemy::getName).toList();
        assertEquals(List.of("Goblin", "Orco", "Troll"), names);
    }

    @Test
    void forestRewardsAreEnoughToReachLevelThree() {
        int totalExperience = Arenas.forest().createEnemies().stream()
                .mapToInt(Enemy::getExperienceReward)
                .sum();
        assertEquals(70, totalExperience);
    }

    @Test
    void eachCallCreatesEnemiesWithFullHealth() {
        Arenas.forest().createEnemies().get(0).takeDamage(10);
        assertEquals(30, Arenas.forest().createEnemies().get(0).getHealth());
    }
}
