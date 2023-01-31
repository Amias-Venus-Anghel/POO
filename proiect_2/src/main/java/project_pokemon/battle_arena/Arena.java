package project_pokemon.battle_arena;

import project_pokemon.import_export.Import;
import project_pokemon.logger.LogBuilder;
import project_pokemon.logger.Logger;
import project_pokemon.trainer.observer.BattleObserver;
import project_pokemon.trainer.observer.BattleSubscriber;
import project_pokemon.trainer.PokemonTrainer;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Arena implements BattleSubscriber {
    private ArrayList<PokemonTrainer> trainers;
    private static final EventGenerator eventGenerator = new EventGenerator();
    private static EventGenerator.Event currentEvent;
    private boolean skipDuel;


    /**
     * Constructor;
     * Imports arena's pokemons and the trainers with all their corresponding
     * info.
     *
     * @param trainersPath path to input file
     */
    public Arena(String trainersPath) {
        this.trainers = Import.importTrainers(trainersPath);
        ArenaPokemons.importArenaPokemons();
    }


    /**
     * Simulates the adventure and prints the battle log.
     *
     * @param printMode true for printing the battle log in a file,
     *                  false otherwise
     */
    public void adventure(boolean printMode) {
        PokemonTrainer trainer1 = this.trainers.get(0);
        PokemonTrainer trainer2 = this.trainers.get(1);

        /* create the common log for both trainers and assign it to them */
        LogBuilder arenaLog = new LogBuilder();
        trainer1.setArenaLog(arenaLog);
        trainer2.setArenaLog(arenaLog);

        applyObserver();

        int progress = 0;
        showLoading(progress);
        for (int i = 0; i < 3; i++) {
            skipDuel = false;
            arenaLog.registerComment(trainer1.presentPokemon() + "\n");
            arenaLog.registerComment(trainer2.presentPokemon() + "\n");
            arenaLog.saveData();
            /* Generate a random event
             * Stops when event is Battle */
            currentEvent = eventGenerator.generateEvent();
            while (!currentEvent.equals(EventGenerator.Event.Battle)) {
                runBattles(trainer1, trainer2);
                currentEvent = eventGenerator.generateEvent();
                if(skipDuel)
                    break;
            }
            showLoading(progress += 20);

            if(skipDuel)
                break;
            /* Battle starts */
            arenaLog.registerComment("Trainer " + trainer1.getName() +
                    " vs Trainer " + trainer2.getName() + "\n");
            duel(trainer1, trainer2);
            showLoading(progress += 10);
        }
        /* last duel */
        /* get and add to the log the best pokemon for each trainer */
        trainer1.setBestPokemon();
        trainer2.setBestPokemon();
        arenaLog.registerComment("Traner " + trainer1.getName()
                + "'s best pokemon: " +
                trainer1.getPokemons().get(trainer1.getPokemonIndex())
                        .getName() + "\n");
        arenaLog.registerComment("Trainer " + trainer2.getName()
                + "'s best pokemon: " +
                trainer2.getPokemons().get(trainer2.getPokemonIndex())
                        .getName() + "\n");
        duel(trainer1, trainer2);
        showLoading(progress += 10);

        /* print the adventure log */
        Logger.getLogger().setPrintMode(printMode);
        Logger.getLogger().printLog();
    }

    private void applyObserver(){
        BattleObserver obs = new BattleObserver();
        obs.addSubscriber(trainers.get(1));
        obs.addSubscriber(trainers.get(0));
        obs.addSubscriber(this);
    }

    /**
     * Starts the battle for each pokemon trainer.
     *
     * @param trainer1 pokemon trainer 1
     * @param trainer2 pokemon trainer 2
     */
    private void runBattles(PokemonTrainer trainer1, PokemonTrainer trainer2) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(trainer1);
        executor.execute(trainer2);
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    /**
     * Starts a duel between 2 trainers.
     *
     * @param trainer1 pokemon trainer 1
     * @param trainer2 pokemon trainer 2
     */
    private void duel(PokemonTrainer trainer1, PokemonTrainer trainer2) {
        trainer1.setEnemy(trainer2.getPokemons().get(trainer2.getPokemonIndex()));
        trainer2.setEnemy(trainer1.getPokemons().get(trainer1.getPokemonIndex()));

        Lock lock = new ReentrantLock();
        trainer1.lock = lock;
        trainer2.lock = lock;
        runBattles(trainer1, trainer2);
    }

    /**
     * Shows loading procent on standard output.
     *
     * @param progress the procent of progress done
     */
    private void showLoading(int progress) {
        if (progress == 0)
            System.out.println("PROGRESS:\t" + progress + "%");
        else
            System.out.println("\t\t\t" + progress + "%");
    }

    /**
     * @return current event taking place in the arena
     */
    public static EventGenerator.Event getCurrentEvent() {
        return currentEvent;
    }

    @Override
    public String toString() {
        return "Trainers in the arena:" + trainers;
    }

    @Override
    public void getUpdate(String state) {
        this.skipDuel = true;
    }

    @Override
    public void setObserver(BattleObserver observer) {

    }
}
