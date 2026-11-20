package it.unicam.cs.mpgc.rpg125820.persistence;

import it.unicam.cs.mpgc.rpg125820.model.HeroType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JsonSaveRepositoryTest {

    @TempDir
    Path tempDir;

    private final GameSnapshot snapshot =
            new GameSnapshot(HeroType.MAGE, "Maga", 3, 60, 45, 15, 2, 4);

    @Test
    void savedSnapshotCanBeLoadedBack() {
        JsonSaveRepository repository = new JsonSaveRepository(tempDir.resolve("save.json"));
        repository.save(snapshot);
        assertEquals(snapshot, repository.load().orElseThrow());
    }

    @Test
    void loadWithoutSaveFileIsEmpty() {
        JsonSaveRepository repository = new JsonSaveRepository(tempDir.resolve("missing.json"));
        assertTrue(repository.load().isEmpty());
    }

    @Test
    void saveCreatesMissingDirectories() {
        Path file = tempDir.resolve("nested").resolve("dir").resolve("save.json");
        JsonSaveRepository repository = new JsonSaveRepository(file);
        repository.save(snapshot);
        assertTrue(Files.exists(file));
    }

    @Test
    void newSaveOverwritesThePreviousOne() {
        JsonSaveRepository repository = new JsonSaveRepository(tempDir.resolve("save.json"));
        repository.save(snapshot);
        GameSnapshot newer = new GameSnapshot(HeroType.WARRIOR, "Guerriero", 1, 0, 100, 20, 3, 0);
        repository.save(newer);
        assertEquals(newer, repository.load().orElseThrow());
    }

    @Test
    void emptyFileIsRejected() throws IOException {
        Path file = tempDir.resolve("save.json");
        Files.writeString(file, "");
        assertThrows(IllegalStateException.class, () -> new JsonSaveRepository(file).load());
    }

    @Test
    void malformedJsonIsRejected() throws IOException {
        Path file = tempDir.resolve("save.json");
        Files.writeString(file, "{ non e json");
        assertThrows(IllegalStateException.class, () -> new JsonSaveRepository(file).load());
    }

    @Test
    void jsonWithMissingFieldsIsRejected() throws IOException {
        Path file = tempDir.resolve("save.json");
        Files.writeString(file, "{\"level\": 2}");
        assertThrows(IllegalStateException.class, () -> new JsonSaveRepository(file).load());
    }

    @Test
    void jsonWithInvalidValuesIsRejected() throws IOException {
        Path file = tempDir.resolve("save.json");
        Files.writeString(file, "{\"heroType\":\"MAGE\",\"heroName\":\"Maga\",\"level\":0,"
                + "\"experience\":0,\"health\":10,\"mana\":5,\"potions\":1,\"defeatedEnemies\":0}");
        assertThrows(IllegalStateException.class, () -> new JsonSaveRepository(file).load());
    }
}
