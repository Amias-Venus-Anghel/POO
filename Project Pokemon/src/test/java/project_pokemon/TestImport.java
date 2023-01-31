package project_pokemon;

import org.junit.jupiter.api.Test;
import project_pokemon.import_export.Import;
import project_pokemon.trainer.pokemon.Pokemon;
import project_pokemon.trainer.PokemonTrainer;

import java.util.ArrayList;
import java.util.HashMap;

public class TestImport {
    @Test
    public void testImportPokemons(){
        String path = "src/main/resources/pokemons.json";
        HashMap<String, Pokemon> pokemons = Import.importPokemons(path);
        System.out.println(pokemons.toString());
    }
    @Test
    public void testImportTrainers(){
        String path = "src/main/resources/pokemon_trainers/trainers_1.json";
        ArrayList<PokemonTrainer> trainers = Import.importTrainers(path);
        System.out.println(trainers.get(0).getPokemons().get(0).toString());
    }
}
