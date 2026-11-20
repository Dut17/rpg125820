package it.unicam.cs.mpgc.rpg125820.persistence;

import it.unicam.cs.mpgc.rpg125820.model.HeroType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSnapshotTest {

    @Test
    void validSnapshotKeepsItsValues() {
        GameSnapshot snapshot = new GameSnapshot(HeroType.MAGE, "Maga", 2, 25, 60, 15, 2, 1);
        assertEquals(HeroType.MAGE, snapshot.heroType());
        assertEquals("Maga", snapshot.heroName());
        assertEquals(2, snapshot.level());
        assertEquals(1, snapshot.defeatedEnemies());
    }

    @Test
    void blankHeroNameIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameSnapshot(HeroType.WARRIOR, " ", 1, 0, 100, 20, 3, 0));
    }

    @Test
    void invalidNumbersAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameSnapshot(HeroType.WARRIOR, "Eroe", 0, 0, 100, 20, 3, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new GameSnapshot(HeroType.WARRIOR, "Eroe", 1, 0, -1, 20, 3, 0));
    }
}
