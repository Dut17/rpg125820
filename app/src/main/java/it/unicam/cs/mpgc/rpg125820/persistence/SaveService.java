package it.unicam.cs.mpgc.rpg125820.persistence;

import it.unicam.cs.mpgc.rpg125820.battle.Game;
import it.unicam.cs.mpgc.rpg125820.battle.GameState;
import it.unicam.cs.mpgc.rpg125820.model.Arena;
import it.unicam.cs.mpgc.rpg125820.model.Hero;
import it.unicam.cs.mpgc.rpg125820.model.HeroType;

import java.util.Objects;
import java.util.Optional;

/**
 * Collega la partita al salvataggio: trasforma un {@link Game} in un
 * {@link GameSnapshot} e viceversa. Non sa dove o come i dati vengono scritti,
 * se ne occupa il {@link SaveRepository}.
 */
public class SaveService {

    private final SaveRepository repository;

    public SaveService(SaveRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    /**
     * Salva una partita in corso. Il nemico corrente non viene salvato:
     * alla ripresa riparte con la vita piena.
     *
     * @param heroType il ruolo scelto, che l'eroe da solo non sa dire
     * @throws IllegalStateException se la partita e gia finita
     */
    public void save(Game game, HeroType heroType) {
        Objects.requireNonNull(game, "game");
        Objects.requireNonNull(heroType, "heroType");
        if (game.getState() != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Si puo salvare solo una partita in corso");
        }
        Hero hero = game.getHero();
        repository.save(new GameSnapshot(heroType, hero.getName(), hero.getLevel(),
                hero.getExperience(), hero.getHealth(), hero.getMana(), hero.getPotions(),
                game.getDefeatedEnemies()));
    }

    /**
     * Riprende l'ultima partita salvata nell'arena data.
     *
     * @return la partita ripresa con il ruolo dell'eroe, oppure vuoto se non esiste nessun salvataggio
     * @throws IllegalStateException se il salvataggio non e coerente con l'arena o con le regole
     */
    public Optional<LoadedGame> load(Arena arena) {
        Objects.requireNonNull(arena, "arena");
        return repository.load().map(snapshot -> restore(snapshot, arena));
    }

    private LoadedGame restore(GameSnapshot snapshot, Arena arena) {
        try {
            Hero hero = snapshot.heroType().create(snapshot.heroName());
            hero.restoreState(snapshot.level(), snapshot.experience(), snapshot.health(),
                    snapshot.mana(), snapshot.potions());
            Game game = new Game(hero, arena, snapshot.defeatedEnemies());
            return new LoadedGame(game, snapshot.heroType());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Il salvataggio non e coerente", e);
        }
    }
}
