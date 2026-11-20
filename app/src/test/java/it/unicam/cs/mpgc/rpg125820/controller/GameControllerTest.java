package it.unicam.cs.mpgc.rpg125820.controller;

import it.unicam.cs.mpgc.rpg125820.battle.GameState;
import it.unicam.cs.mpgc.rpg125820.battle.HeroAction;
import it.unicam.cs.mpgc.rpg125820.battle.TurnResult;
import it.unicam.cs.mpgc.rpg125820.model.Arena;
import it.unicam.cs.mpgc.rpg125820.model.EnemyTemplate;
import it.unicam.cs.mpgc.rpg125820.model.HeroType;
import it.unicam.cs.mpgc.rpg125820.persistence.GameSnapshot;
import it.unicam.cs.mpgc.rpg125820.persistence.SaveRepository;
import it.unicam.cs.mpgc.rpg125820.persistence.SaveService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private static final EnemyTemplate GOBLIN = new EnemyTemplate("Goblin", 10, 5, 0, 15);
    private static final EnemyTemplate ORC = new EnemyTemplate("Orco", 10, 5, 0, 20);
    private static final EnemyTemplate GOLEM = new EnemyTemplate("Golem", 500, 1, 0, 10);

    private static class InMemoryRepository implements SaveRepository {
        private GameSnapshot saved;

        @Override
        public void save(GameSnapshot snapshot) {
            saved = snapshot;
        }

        @Override
        public Optional<GameSnapshot> load() {
            return Optional.ofNullable(saved);
        }
    }

    private final SaveService saveService = new SaveService(new InMemoryRepository());
    private final Arena arena = new Arena("Foresta", List.of(GOBLIN, ORC));
    private final GameController controller = new GameController(saveService, arena);

    @Test
    void newGameStartsInProgressWithTheChosenHero() {
        controller.startNewGame(HeroType.WARRIOR, "Eroe");
        assertTrue(controller.hasGame());
        assertEquals(GameState.IN_PROGRESS, controller.getGame().getState());
        assertEquals(HeroType.WARRIOR, controller.getHeroType());
        assertEquals("Eroe", controller.getGame().getHero().getName());
    }

    @Test
    void invalidNameKeepsTheCurrentGame() {
        controller.startNewGame(HeroType.WARRIOR, "Eroe");
        assertThrows(IllegalArgumentException.class, () -> controller.startNewGame(HeroType.MAGE, " "));
        assertEquals(HeroType.WARRIOR, controller.getHeroType());
        assertEquals("Eroe", controller.getGame().getHero().getName());
    }

    @Test
    void playAdvancesTheGame() {
        controller.startNewGame(HeroType.WARRIOR, "Eroe");
        TurnResult result = controller.play(HeroAction.ATTACK);
        assertTrue(result.damageDealt() > 0);
        assertEquals(1, controller.getGame().getDefeatedEnemies());
    }

    @Test
    void canPerformFollowsManaAndPotions() {
        GameController longGame = new GameController(saveService, new Arena("Roccia", List.of(GOLEM)));
        assertFalse(longGame.canPerform(HeroAction.ATTACK));

        longGame.startNewGame(HeroType.WARRIOR, "Eroe");
        assertTrue(longGame.canPerform(HeroAction.ATTACK));
        assertTrue(longGame.canPerform(HeroAction.SPECIAL));
        assertTrue(longGame.canPerform(HeroAction.POTION));

        longGame.play(HeroAction.SPECIAL);
        longGame.play(HeroAction.SPECIAL);
        assertFalse(longGame.canPerform(HeroAction.SPECIAL));

        longGame.play(HeroAction.POTION);
        longGame.play(HeroAction.POTION);
        longGame.play(HeroAction.POTION);
        assertFalse(longGame.canPerform(HeroAction.POTION));
        assertTrue(longGame.canPerform(HeroAction.ATTACK));
    }

    @Test
    void nothingIsPossibleWhenTheGameIsOver() {
        GameController shortGame = new GameController(saveService, new Arena("Foresta", List.of(GOBLIN)));
        shortGame.startNewGame(HeroType.WARRIOR, "Eroe");
        shortGame.play(HeroAction.ATTACK);

        assertEquals(GameState.VICTORY, shortGame.getGame().getState());
        assertFalse(shortGame.canPerform(HeroAction.ATTACK));
        assertThrows(IllegalStateException.class, () -> shortGame.play(HeroAction.ATTACK));
        assertThrows(IllegalStateException.class, shortGame::saveGame);
    }

    @Test
    void savedGameCanBeLoadedByAnotherController() {
        controller.startNewGame(HeroType.MAGE, "Maga");
        controller.play(HeroAction.ATTACK);
        controller.saveGame();

        GameController other = new GameController(saveService, arena);
        assertFalse(other.hasGame());
        assertTrue(other.loadGame());
        assertEquals(HeroType.MAGE, other.getHeroType());
        assertEquals("Maga", other.getGame().getHero().getName());
        assertEquals(1, other.getGame().getDefeatedEnemies());
    }

    @Test
    void loadWithoutSaveReturnsFalse() {
        assertFalse(controller.loadGame());
        assertFalse(controller.hasGame());
    }

    @Test
    void methodsThatNeedAGameFailWithoutOne() {
        assertThrows(IllegalStateException.class, controller::getGame);
        assertThrows(IllegalStateException.class, controller::getHeroType);
        assertThrows(IllegalStateException.class, controller::saveGame);
        assertThrows(IllegalStateException.class, () -> controller.play(HeroAction.ATTACK));
    }

    @Test
    void nullArgumentsAreRejected() {
        assertThrows(NullPointerException.class, () -> new GameController(null, arena));
        assertThrows(NullPointerException.class, () -> new GameController(saveService, null));
        assertThrows(NullPointerException.class, () -> controller.startNewGame(null, "Eroe"));
        assertThrows(NullPointerException.class, () -> controller.play(null));
        assertThrows(NullPointerException.class, () -> controller.canPerform(null));
    }
}
