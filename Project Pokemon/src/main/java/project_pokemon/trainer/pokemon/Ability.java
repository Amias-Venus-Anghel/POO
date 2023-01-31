package project_pokemon.trainer.pokemon;

public class Ability {
    private int damage;
    private boolean stun;
    private boolean dodge;
    private int cooldown;
    private int recharge; /* used for checking if the cooldown has passed */


    public Ability(int damage, boolean stun, boolean dodge, int cooldown) {
        this.damage = damage;
        this.stun = stun;
        this.dodge = dodge;
        this.cooldown = cooldown;
        this.recharge = 0;
    }

    public int getDamage() {
        return damage;
    }

    public boolean hasStun() {
        return stun;
    }

    public boolean hasDodge() {
        return dodge;
    }

    public boolean readyToUse(){ return recharge <= 0;}

    public int getCooldown() {
        return cooldown;
    }

    public int getRecharge() {
        return recharge;
    }

    public void setRecharge(int recharge) {
        this.recharge = recharge;
    }

    public void recharging() {
        this.recharge--;
    }

}
