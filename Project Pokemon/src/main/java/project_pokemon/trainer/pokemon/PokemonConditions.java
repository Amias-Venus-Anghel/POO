package project_pokemon.trainer.pokemon;

public class PokemonConditions{
    private boolean isStun;
    private boolean dodge;
    private boolean willBeStunned;
    private boolean willDodge;

    public void update(){
        isStun = willBeStunned;
        dodge = willDodge;
        willBeStunned = false;
        willDodge = false;
    }

    public boolean isStun() {
        return isStun;
    }

    public boolean hasDodge() {
        return dodge;
    }

    public void setWillBeStunned() {
        this.willBeStunned = true;
    }

    public void setWillDodge() {
        this.willDodge = true;
    }
}
