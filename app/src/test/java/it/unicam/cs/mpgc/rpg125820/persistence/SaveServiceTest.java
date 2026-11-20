package it.unicam.cs.mpgc.rpg125820.persistence;

import it.unicam.cs.mpgc.rpg125820.battle.Game;
import it.unicam.cs.mpgc.rpg125820.battle.GameState;
import it.unicam.cs.mpgc.rpg125820.battle.HeroAction;
import it.unicam.cs.mpgc.rpg125820.model.Arena;
import it.unicam.cs.mpgc.rpg125820.model.EnemyTemplate;
import it.unicam.cs.mpgc.rpg125820.model.Hero;
import it.unicam.cs.mpgc.rpg125820.model.HeroType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SaveServiceTest {

    private static final EnemyTemplate GOBLIN = new EnemyTemplate("Goblin", 10, 5, 0, 15);
    private static final EnemyTemplate ORC = new EnemyTemplate("Orco", 10, 5, 0, 20);

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

    private final InMemoryRepository repository = new InMemoryRepository();
    private final SaveService service = new SaveService(repository);
    private final Arena arena = new Arena("Foresta", List.of(GOBLIN, ORC));

    @Test
    void savedGameIsResumedWithTheSameState() {
        Game original = new Game(HeroType.WARRIOR.create("Eroe"), arena);
        original.playTurn(HeroAction.ATTACK);

        service.save(original, HeroType.WARRIOR);
        Game resumed = service.load(arena).orElseThrow().game();

        Hero before = original.getHero();
        Hero after = resumed.getHero();
        assertEquals(before.getName(), after.getName());
        assertEquals(before.getLevel(), after.getLevel());
        assertEquals(before.getExperience(), after.getExperience());
        assertEquals(before.getHealth(), after.getHealth());
        assertEquals(before.getMana(), after.getMana());
        assertEquals(before.getPotions(), after.getPotions());
        assertEquals(original.getDefeatedEnemies(), resumed.getDefeatedEnemies());
        assertEquals("Orco", resumed.getCurrentEnemy().getName());
        assertEquals(GameState.IN_PROGRESS, resumed.getState());
    }

    @Test
    void loadedGameKeepsTheHeroType() {
        Game original = new Game(HeroType.MAGE.create("Maga"), arena);
        service.save(original, HeroType.MAGE);
        assertEquals(HeroType.MAGE, service.load(arena).orElseThrow().heroType());
    }

    @Test
    void loadWithoutSaveIsEmpty() {
        assertTrue(service.load(arena).isEmpty());
    }

    @Test
    void finishedGameCannotBeSaved() {
        Game game = new Game(HeroType.WARRIOR.create("Eroe"), new Arena("Foresta", List.of(GOBLIN)));
        game.playTurn(HeroAction.ATTACK);
        assertEquals(GameState.VICTORY, game.getState());
        assertThrows(IllegalStateException.class, () -> service.save(game, HeroType.WARRIOR));
        assertTrue(repository.load().isEmpty());
    }

    @Test
    void incoherentSnapshotIsRejected() {
        repository.save(new GameSnapshot(HeroType.WARRIOR, "Eroe", 1, 0, 100, 20, 3, 5));
        assertThrows(IllegalStateException.class, () -> service.load(arena));

        repository.save(new GameSnapshot(HeroType.WARRIOR, "Eroe", 1, 0, 9999, 20, 3, 0));
        assertThrows(IllegalStateException.class, () -> service.load(arena));
    }

    @Test
    void nullArgumentsAreRejected() {
        Game game = new Game(HeroType.WARRIOR.create("Eroe"), arena);
        assertThrows(NullPointerException.class, () -> new SaveService(null));
        assertThrows(NullPointerException.class, () -> service.save(null, HeroType.WARRIOR));
        assertThrows(NullPointerException.class, () -> service.save(game, null));
        assertThrows(NullPointerException.class, () -> service.load(null));
    }
}
