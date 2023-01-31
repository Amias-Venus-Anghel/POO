package project_pokemon.import_export;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import project_pokemon.trainer.observer.BattleObserver;
import project_pokemon.trainer.pokemon.Pokemon;
import project_pokemon.trainer.PokemonTrainer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Import {
    /**
     * Imports a list of pokemons.
     * @param path path to the input file of type json
     * @return pokemons maped by their name
     */
    public static HashMap<String, Pokemon> importPokemons(String path) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            ArrayList<Pokemon> pokemons = gson.fromJson(reader, new TypeToken<List<Pokemon>>() {
            }.getType());
            reader.close();

            /* add pokemon to map and initialize their conditions */
            HashMap<String, Pokemon> pokemonMap = new HashMap<>();
            for (Pokemon p : pokemons) {
                pokemonMap.put(p.getName(), p);
                p.initConditions();
            }

            return pokemonMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Imports a list of trainers and their pokemon.
     * @param path path to input file of type json
     * @return list of pokemon trainers
     */
    public static ArrayList<PokemonTrainer> importTrainers(String path) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(path));
            ArrayList<PokemonTrainer> trainers = gson.fromJson(reader, new TypeToken<List<PokemonTrainer>>() {
            }.getType());
            reader.close();
            /* add bonuses from objects, save the base HP
            and initialize status conditions */
            for(PokemonTrainer pt : trainers){
                for(Pokemon p : pt.getPokemons()){
                    p.addBonuses();
                    p.initConditions();
                }
            }
            return trainers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
