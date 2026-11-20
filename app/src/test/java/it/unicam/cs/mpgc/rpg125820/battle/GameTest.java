package it.unicam.cs.mpgc.rpg125820.battle;

import it.unicam.cs.mpgc.rpg125820.model.Arena;
import it.unicam.cs.mpgc.rpg125820.model.EnemyTemplate;
import it.unicam.cs.mpgc.rpg125820.model.Mage;
import it.unicam.cs.mpgc.rpg125820.model.Warrior;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static final EnemyTemplate GOBLIN = new EnemyTemplate("Goblin", 10, 5, 0, 15);
    private static final EnemyTemplate ORC = new EnemyTemplate("Orco", 10, 5, 0, 20);
    private static final EnemyTemplate DRAGON = new EnemyTemplate("Drago", 500, 100, 0, 50);

    @Test
    void newGameStartsWithFirstEnemy() {
        Game game = new Game(new Warrior("Eroe"), new Arena("Foresta", List.of(GOBLIN, ORC)));
        assertEquals(GameState.IN_PROGRESS, game.getState());
        assertEquals("Goblin", game.getCurrentEnemy().getName());
        assertEquals(0, game.getDefeatedEnemies());
        assertEquals(2, game.getTotalEnemies());
    }

    @Test
    void defeatingAnEnemyMovesToTheNextOne() {
        Game game = new Game(new Warrior("Eroe"), new Arena("Foresta", List.of(GOBLIN, ORC)));
        game.playTurn(HeroAction.ATTACK);
        assertEquals(GameState.IN_PROGRESS, game.getState());
        assertEquals(1, game.getDefeatedEnemies());
        assertEquals("Orco", game.getCurrentEnemy().getName());
    }

    @Test
    void defeatingTheLastEnemyGivesVictory() {
        Game game = new Game(new Warrior("Eroe"), new Arena("Foresta", List.of(GOBLIN)));
        game.playTurn(HeroAction.ATTACK);
        assertEquals(GameState.VICTORY, game.getState());
    }

    @Test
    void defeatedEnemyGivesExperienceToTheHero() {
        Warrior hero = new Warrior("Eroe");
        Game game = new Game(hero, new Arena("Foresta", List.of(GOBLIN, ORC)));
        game.playTurn(HeroAction.ATTACK);
        assertEquals(15, hero.getExperience());
        assertEquals(1, hero.getLevel());
    }

    @Test
    void enoughExperienceLevelsTheHeroUp() {
        Warrior hero = new Warrior("Eroe");
        Game game = new Game(hero, new Arena("Foresta", List.of(GOBLIN, ORC)));
        game.playTurn(HeroAction.ATTACK);
        game.playTurn(HeroAction.ATTACK);
        assertEquals(2, hero.getLevel());
    }

    @Test
    void heroDeathGivesDefeatWithoutExperience() {
        Mage hero = new Mage("Maga");
        Game game = new Game(hero, new Arena("Caverna", List.of(DRAGON)));
        game.playTurn(HeroAction.ATTACK);
        assertEquals(GameState.DEFEAT, game.getState());
        assertEquals(0, hero.getExperience());
    }

    @Test
    void playingAfterTheEndFails() {
        Game game = new Game(new Warrior("Eroe"), new Arena("Foresta", List.of(GOBLIN)));
        game.playTurn(HeroAction.ATTACK);
        assertThrows(IllegalStateException.class, () -> game.playTurn(HeroAction.ATTACK));
    }

    @Test
    void constructorRejectsNullArguments() {
        Arena arena = new Arena("Foresta", List.of(GOBLIN));
        assertThrows(NullPointerException.class, () -> new Game(null, arena));
        assertThrows(NullPointerException.class, () -> new Game(new Warrior("Eroe"), null));
    }
}
