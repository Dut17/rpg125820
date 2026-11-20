package it.unicam.cs.mpgc.rpg125820.battle;

import it.unicam.cs.mpgc.rpg125820.model.Arena;
import it.unicam.cs.mpgc.rpg125820.model.Enemy;
import it.unicam.cs.mpgc.rpg125820.model.Hero;

import java.util.List;
import java.util.Objects;

/**
 * Partita in una arena: l'eroe affronta i nemici uno dopo l'altro.
 * Contiene solo logica di gioco, nessun input e nessun output.
 */
public class Game {

    private final Hero hero;
    private final List<Enemy> enemies;
    private int defeatedEnemies;
    private Battle battle;
    private GameState state = GameState.IN_PROGRESS;

    public Game(Hero hero, Arena arena) {
        this(hero, arena, 0);
    }

    /**
     * Riprende una partita: i primi nemici sono gia stati sconfitti, il nemico
     * corrente parte con la vita piena.
     *
     * @param defeatedEnemies nemici gia sconfitti, da 0 a numero di nemici - 1
     */
    public Game(Hero hero, Arena arena, int defeatedEnemies) {
        this.hero = Objects.requireNonNull(hero, "hero");
        this.enemies = Objects.requireNonNull(arena, "arena").createEnemies();
        if (defeatedEnemies < 0 || defeatedEnemies >= enemies.size()) {
            throw new IllegalArgumentException("Numero di nemici sconfitti non valido");
        }
        this.defeatedEnemies = defeatedEnemies;
        this.battle = new Battle(hero, enemies.get(defeatedEnemies));
    }

    public Hero getHero() {
        return hero;
    }

    public GameState getState() {
        return state;
    }

    public Enemy getCurrentEnemy() {
        return battle.getEnemy();
    }

    public int getDefeatedEnemies() {
        return defeatedEnemies;
    }

    public int getTotalEnemies() {
        return enemies.size();
    }

    /**
     * Gioca un turno contro il nemico corrente. Se il nemico muore l'eroe
     * guadagna esperienza e la partita passa al nemico successivo.
     *
     * @throws IllegalStateException se la partita e finita o l'azione non e possibile
     */
    public TurnResult playTurn(HeroAction action) {
        if (state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("La partita e gia finita");
        }
        TurnResult result = battle.playTurn(action);
        if (battle.isOver()) {
            endCurrentBattle();
        }
        return result;
    }

    private void endCurrentBattle() {
        if (!battle.isHeroWinner()) {
            state = GameState.DEFEAT;
            return;
        }
        hero.gainExperience(battle.getEnemy().getExperienceReward());
        defeatedEnemies++;
        if (defeatedEnemies == enemies.size()) {
            state = GameState.VICTORY;
        } else {
            battle = new Battle(hero, enemies.get(defeatedEnemies));
        }
    }
}
