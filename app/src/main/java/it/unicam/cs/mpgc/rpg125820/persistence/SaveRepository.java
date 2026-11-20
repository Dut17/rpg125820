package it.unicam.cs.mpgc.rpg125820.persistence;

import java.util.Optional;

/**
 * Salva e carica lo stato di una partita. Le implementazioni decidono dove e
 * in che formato (file JSON, database, ...), il resto del gioco non lo sa.
 */
public interface SaveRepository {

    void save(GameSnapshot snapshot);

    /**
     * @return l'ultimo salvataggio, oppure vuoto se non ne esiste nessuno
     */
    Optional<GameSnapshot> load();
}
