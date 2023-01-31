package project_pokemon.battle_arena;

import project_pokemon.import_export.Import;
import project_pokemon.trainer.pokemon.Pokemon;
import project_pokemon.trainer.pokemon.PokemonBuilder;

import java.util.HashMap;

public class ArenaPokemons {
    private static HashMap<String, Pokemon> arenaPokemons;

    /**
     * Imports the arena pokemons.
     */
    public static void importArenaPokemons() {
        arenaPokemons = Import.importPokemons
                ("src/main/resources/arena_pokemons.json");
    }

    /**
     * Builds and returns an arena pokemon depending on the current event.
     * @param event current arena event
     * @return an arena pokemon
     */
    public static Pokemon getArenaPokemon(EventGenerator.Event event) {
        String name = event.toString();
        Pokemon arenaPokemon = arenaPokemons.get(name);
        PokemonBuilder pb = new PokemonBuilder();
        return pb.withName(arenaPokemon.getName())
                .withHP(arenaPokemon.getHp())
                .hasSpecialAttack(arenaPokemon.hasSpecialAtt())
                .withAttack(arenaPokemon.getAttack())
                .withDefence(arenaPokemon.getDefence())
                .withSpecialDefence(arenaPokemon.getSpecialDefence())
                .build();
    }
}
