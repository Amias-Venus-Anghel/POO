package project_pokemon.trainer;

import project_pokemon.battle_arena.Arena;
import project_pokemon.battle_arena.ArenaPokemons;
import project_pokemon.battle_arena.EventGenerator;
import project_pokemon.logger.LogBuilder;
import project_pokemon.trainer.observer.BattleObserver;
import project_pokemon.trainer.observer.BattleSubscriber;
import project_pokemon.trainer.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.locks.Lock;

public class PokemonTrainer implements Runnable, BattleSubscriber {
    private final String name;
    private final int age;
    private final ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>(3);
    private int duels = 0;
    private int pIndex = 0;
    private Pokemon enemy;
    private LogBuilder battleLog;
    private LogBuilder arenaLog;
    public Lock lock;
    private boolean enemyState = false;
    private BattleObserver observer;

    public PokemonTrainer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Starts the corresponding battle type depending on the number of duels
     * the trainer had.
     */
    @Override
    public void run() {
        try {
            initBattleLog();
            if (duels < 3) {
                /* get the pokemon who will battle */
                Pokemon pokemon = pokemons.get(pIndex);
                EventGenerator.Event event = Arena.getCurrentEvent();
                switch (event) {
                    case Battle:
                        trainerDuel();
                        /* change duel number and pokemon index */
                        duels++;
                        pIndex++;
                        break;
                    default:
                        /* start event of battling with an arena pokemon */
                        battleLog.registerComment("Pokemon trainer " + name
                                + " is in an arena battle\n");
                        battleLog.registerComment(pokemon.toString() + "\n");
                        enemy = ArenaPokemons.getArenaPokemon(event);
                        battleLog.register(pokemon.getName());
                        battleLog.register(pokemon.getHp() + "");
                        battleLog.register(enemy.getName());
                        battleLog.register(enemy.getHp() + "");
                        battleEnemy(enemy);
                        break;
                }
            } else {
                trainerDuel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simulates a duel between trainers and logs the events of the duel.
     *
     * @throws InterruptedException
     */
    private synchronized void trainerDuel() throws InterruptedException {
        Pokemon pokemon = this.pokemons.get(pIndex);
        lock.lock();
        arenaLog.registerComment(pokemon.toString());
        lock.unlock();
        wait(150);
        lock.lock();
        arenaLog.register(pokemon.getName());
        arenaLog.register(pokemon.getHp() + "");
        lock.unlock();
        wait(150);
        /* fighting on turns */
        while (!enemy.hasFainted() && !pokemon.hasFainted()) {
            lock.lock();
            arenaLog.register(pokemon.getName());
            arenaLog.register(pokemon.attackEnemy(enemy));
            lock.unlock();
            wait(150);
            pokemon.updateCondition();
            lock.lock();
            arenaLog.register(pokemon.getStatus());
            lock.unlock();
            wait(150);
        }

        /* log result */
        lock.lock();
        if (enemy.hasFainted()) {
            if (pokemon.hasFainted()) {
                arenaLog.registerComment("Both pokemon fainted. " +
                        "There is no winner.\n");
            } else {
                arenaLog.registerComment("\t" + pokemon.getName()
                        + " won\n");
                pokemon.levelUp();
                arenaLog.registerComment(pokemon.toString());
                arenaLog.registerComment("TRAINER " +
                        this.toString().toUpperCase(Locale.ROOT) +
                        " WINS THE BATTLE\n");
            }
            arenaLog.saveData();
            pokemon.fullHeal();
            enemy.fullHeal();
        }
        lock.unlock();
    }


    /**
     * Simulates the battle with a given enemy and logs the events of the
     * battle.
     *
     * @param enemy enemy pokemon to battle
     * @throws InterruptedException
     */
    private void battleEnemy(Pokemon enemy) throws InterruptedException {
        Pokemon pokemon = this.pokemons.get(pIndex);
        while (!enemy.hasFainted() && !pokemon.hasFainted() && !enemyState) {
            battleLog.register(pokemon.getName());
            battleLog.register(pokemon.attackEnemy(enemy));
            battleLog.register(enemy.getName());
            battleLog.register(enemy.attackEnemy(pokemon));
            pokemon.updateCondition();
            battleLog.register(pokemon.getStatus());
            enemy.updateCondition();
            battleLog.register(enemy.getStatus());
            Thread.sleep((int) (Math.random() * 1000));
        }

        /* log results */
        if (enemy.hasFainted()) {
            battleLog.registerComment("\t" + pokemon.getName() + " WON\n");
            pokemon.levelUp();
        } else if(pokemon.hasFainted()) {
            battleLog.registerComment("\t" + pokemon.getName() + " LOST\n");
            observer.notify(name);
        } else{
            battleLog.registerComment("\tOpposite pokemon has fainted." +
                    "This duel is won by " + name);
            enemyState = false;
            /* double level up because of automatically won duel */
            pokemon.levelUp();
            pokemon.levelUp();
        }
        battleLog.registerComment(pokemon.toString());
        battleLog.saveData();
        pokemon.fullHeal();
    }

    /**
     * Sets the pokemon index to the trainer's best pokemon.
     */
    public void setBestPokemon() {
        pIndex = 0;
        for (int i = 1; i < pokemons.size(); i++) {
            Pokemon pok1 = pokemons.get(pIndex);
            Pokemon pok2 = pokemons.get(i);
            if (pok2.bestScore() > pok1.bestScore() || (pok2.bestScore() == pok1.bestScore() &&
                    pok1.getName().compareTo(pok2.getName()) < 0)) {
                pIndex = i;
            }
        }
    }

    public String presentPokemon() {
        return "Trainer " + name + " chooses " +
                pokemons.get(pIndex).getName() + ".\n\tequiped: " +
                pokemons.get(pIndex).getItems();
    }

    /* setters and getters */
    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public int getPokemonIndex() {
        return pIndex;
    }

    public String getName() {
        return name;
    }

    private void initBattleLog() {
        if (battleLog == null)
            battleLog = new LogBuilder();
    }

    public void setArenaLog(LogBuilder arenaLog) {
        this.arenaLog = arenaLog;
    }

    public void setEnemy(Pokemon enemyPokemon) {
        this.enemy = enemyPokemon;
    }

    @Override
    public String toString() {
        return name + ", " + age;
    }

    /**
     * Updates the state of the enemy as observed by the observer.
     * True if enemy's pokemon is down, false otherwise;
     *
     * @param state name of the pokemon trainer who's pokemon lost
     */
    @Override
    public void getUpdate(String state) {
        if (!state.equals(name))
            enemyState = true;
    }

    @Override
    public void setObserver(BattleObserver observer) {
        this.observer = observer;
    }
}
