package project_pokemon.trainer.pokemon;

public class PokemonBuilder {
    private Pokemon pokemon = new Pokemon();

    public Pokemon build() {
        pokemon.initConditions();
        return pokemon;
    }

    public PokemonBuilder withName(String name) {
        pokemon.setName(name);
        return this;
    }

    public PokemonBuilder withHP(int hp) {
        pokemon.setStartHP(hp);
        return this;
    }

    public PokemonBuilder hasSpecialAttack(boolean spAtt) {
        pokemon.setHasSpecialAtt(spAtt);
        return this;
    }

    public PokemonBuilder withAttack(int attack){
        pokemon.setAttack(attack);
        return this;
    }

    public PokemonBuilder withDefence(int defence){
        pokemon.setDefence(defence);
        return this;
    }

    public PokemonBuilder withSpecialDefence(int specialDefence){
        pokemon.setSpecialDefence(specialDefence);
        return this;
    }
}
