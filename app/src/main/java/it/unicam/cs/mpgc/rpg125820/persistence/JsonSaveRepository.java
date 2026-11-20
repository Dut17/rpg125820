package it.unicam.cs.mpgc.rpg125820.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * Salva e carica la partita in un file JSON, usando Gson.
 */
public class JsonSaveRepository implements SaveRepository {

    private final Path file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * @param file il file in cui salvare la partita
     */
    public JsonSaveRepository(Path file) {
        this.file = Objects.requireNonNull(file, "file");
    }

    @Override
    public void save(GameSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "snapshot");
        try {
            Path parent = file.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(file, gson.toJson(snapshot), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile salvare la partita", e);
        }
    }

    /**
     * @return l'ultimo salvataggio, oppure vuoto se il file non esiste
     * @throws IllegalStateException se il file esiste ma il contenuto non e valido
     */
    @Override
    public Optional<GameSnapshot> load() {
        if (!Files.exists(file)) {
            return Optional.empty();
        }
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            GameSnapshot snapshot = gson.fromJson(json, GameSnapshot.class);
            if (snapshot == null) {
                throw new IllegalStateException("Il salvataggio e vuoto");
            }
            return Optional.of(snapshot);
        } catch (IOException e) {
            throw new UncheckedIOException("Impossibile leggere il salvataggio", e);
        } catch (IllegalStateException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new IllegalStateException("Il salvataggio non e valido", e);
        }
    }
}
