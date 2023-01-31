package project_pokemon.trainer.pokemon;

import java.util.ArrayList;
import java.util.Random;

public class Pokemon implements Cloneable {
    private String name;
    private int hp;
    private boolean hasSpecialAtt;
    private int attack;
    private int defence;
    private int specialDefence;
    private int wins = 0;
    private int baseHP; /* for level up, base form */
    private PokemonConditions conditions;
    private ArrayList<Ability> abilities;
    private ArrayList<Item> items;

    public Pokemon(String name, int hp, boolean hasSpecialAtt, int attack,
                   int defence, int specialDefence) {
        this.name = name;
        this.hp = hp;
        this.baseHP = hp;
        this.attack = attack;
        this.hasSpecialAtt = hasSpecialAtt;
        this.defence = defence;
        this.specialDefence = specialDefence;
    }

    public Pokemon() {
    }

    /**
     * Initiates status conditions for pokemon.
     */
    public void initConditions() {
        this.conditions = new PokemonConditions();
    }

    /**
     * Adds bonuses for all items if they apply.
     */
    public void addBonuses() {
        if (this.items != null) {
            for (Item i : this.items) {
                this.hp += i.getHp_bonus();
                this.defence += i.getDefence_bonus();
                this.specialDefence += i.getSpecialDef_bonus();
                if (this.hasSpecialAtt)
                    this.attack += i.getSpecialAtt_bonus();
                else
                    this.attack += i.getAttack_bonus();
            }
        }
        this.baseHP = this.hp;
    }

    /**
     * Complety heals the pokemon back to full HP.
     */
    public void fullHeal() {
        this.hp = this.baseHP + this.wins;
        this.conditions.update();
        this.abilities.get(0).setRecharge(0);
        this.abilities.get(1).setRecharge(0);
    }

    /**
     * Levels up the pokemon. When leveled up, the pokemon receives a +1 on all
     * stats.
     */
    public void levelUp() {
        this.wins++;
        this.fullHeal();
        this.attack++;
        this.defence++;
        this.specialDefence++;
    }

    /**
     * Reduces the pokemon's HP when attacked.
     *
     * @param damage  base damaged of the attack
     * @param spAtt   attack is or not a special attack
     * @param ability attack is or not an ability
     */
    public void takeDamage(int damage, boolean spAtt, boolean ability) {
        if (ability) {
            hp -= damage;
        } else {
            if (spAtt) {
                if (damage > specialDefence)
                    hp -= damage - specialDefence;
            } else {
                if (damage > defence)
                    hp -= damage - defence;
            }
        }
        /* HP can't be lower than 0 */
        if (hp < 0)
            hp = 0;
    }

    /**
     * Pokemon uses an ability.
     *
     * @param ability ability to be used
     * @param enemy   enemy to be attacked
     */
    private void useAbility(Ability ability, Pokemon enemy) {
        enemy.takeDamage(ability.getDamage(), false, true);
        if (ability.hasDodge())
            this.setWillDodge();
        if (ability.hasStun())
            enemy.setWillBeStunned();
        ability.setRecharge(ability.getCooldown() + 1);
    }

    /**
     * Checks and gets a random attack type for the pokemon to use:
     * normal/special attack or an ability if it's not in cooldown.
     *
     * @return random attack type
     */
    private int getAttType() {
        Random rand = new Random();
        ArrayList<Integer> options = new ArrayList<>();
        options.add(0); // normal/special attack
        if (abilities != null) {
            if (abilities.get(0).readyToUse())
                options.add(1); // ability 1
            if (abilities.get(1).readyToUse())
                options.add(2); // ability 2
        }
        return options.get(rand.nextInt(options.size()));
    }

    /**
     * Updated the status conditions of a pokemon.
     */
    public void updateCondition() {
        conditions.update();
        /* recharge abilities */
        if (abilities != null) {
            abilities.get(0).recharging();
            abilities.get(1).recharging();
        }
    }

    /**
     * Builds and returns the status of the pokemon, including HP, status
     * conditions and abilities in cooldown.
     *
     * @return current status of the pokemon
     */
    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" HP " + hp);
        if (isStun())
            sb.append(", (stuned)");
        if (willDodge())
            sb.append(", (dodge)");
        if (abilities != null) {
            if (!abilities.get(0).readyToUse())
                sb.append(", ability 1 cooldown " +
                        abilities.get(0).getRecharge());
            if (!abilities.get(1).readyToUse())
                sb.append(", ability 2 cooldown " +
                        abilities.get(1).getRecharge());
        }
        return sb.toString();
    }

    /**
     * Attacks the enemy pokemon.
     *
     * @param enemy enemy pokemon to be attacked
     * @return the attack type used.
     */
    public String attackEnemy(Pokemon enemy) {
        StringBuilder sb = new StringBuilder();
        if (!isStun() && !enemy.willDodge()) {
            int attType = getAttType();
            if (attType == 0) {
                if (this.hasSpecialAtt) {
                    sb.append("special attack");
                    enemy.takeDamage(this.attack, true, false);
                } else {
                    sb.append("normal attack");
                    enemy.takeDamage(this.attack, false, false);
                }
            } else if (attType == 1) {
                sb.append("ability 1");
                useAbility(abilities.get(0), enemy);
            } else if (attType == 2) {
                sb.append("ability 2");
                useAbility(abilities.get(1), enemy);
            }
        } else {
            sb.append("nothing");
        }
        return sb.toString();
    }

    /**
     * Calculated pokemon's stats score.
     *
     * @return stats score
     */
    public int bestScore() {
        return hp + defence + specialDefence + attack;
    }

    /**
     * Gived the current state of the pokemon
     *
     * @return true if pokemon has fainted or false otherwise
     */
    public boolean hasFainted() {
        return hp <= 0;
    }

    /**
     * @return list of items on the pokemon
     */
    public String getItems() {
        StringBuilder sb = new StringBuilder();
        if (items == null || items.isEmpty())
            sb.append("none");
        else
            for (int i = 0; i < items.size();) {
                sb.append(items.get(i).getName());
                if(++i < items.size())
                    sb.append(", ");
            }

        return sb.toString();
    }

    /* getters and setters */
    public boolean isStun() {
        return conditions.isStun();
    }

    public void setWillBeStunned() {
        conditions.setWillBeStunned();
    }

    public boolean willDodge() {
        return conditions.hasDodge();
    }

    public void setWillDodge() {
        conditions.setWillDodge();
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasSpecialAtt() {
        return hasSpecialAtt;
    }

    public void setHasSpecialAtt(boolean hasSpecialAtt) {
        this.hasSpecialAtt = hasSpecialAtt;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getSpecialDefence() {
        return specialDefence;
    }

    public void setSpecialDefence(int specialDefence) {
        this.specialDefence = specialDefence;
    }

    public void setStartHP(int baseHP) {
        this.baseHP = baseHP;
        this.hp = baseHP;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + "'s attributes: ");
        sb.append("HP " + hp + ",");
        if (hasSpecialAtt)
            sb.append(" Special");
        sb.append(" Attack " + attack);
        sb.append(", Defence " + defence);
        sb.append(", Special Defence " + specialDefence);
        sb.append("\n");
        return sb.toString();
    }
}
