package project_pokemon.trainer.pokemon;

import java.util.Objects;

public class Item {
    private String name;
    private int hp_bonus = 0;
    private int attack_bonus = 0;
    private int specialAtt_bonus = 0;
    private int defence_bonus = 0;
    private int specialDef_bonus = 0;

    public Item(String name, int hp_bonus, int attack_bonus,
                int specialAtt_bonus, int defence_bonus, int specialDef_bonus) {
        this.name = name;
        this.hp_bonus = hp_bonus;
        this.attack_bonus = attack_bonus;
        this.specialAtt_bonus = specialAtt_bonus;
        this.defence_bonus = defence_bonus;
        this.specialDef_bonus = specialDef_bonus;
    }

    public String getName() {
        return name;
    }

    public int getHp_bonus() {
        return hp_bonus;
    }
    
    public int getAttack_bonus() {
        return attack_bonus;
    }

    public int getSpecialAtt_bonus() {
        return specialAtt_bonus;
    }

    public int getDefence_bonus() {
        return defence_bonus;
    }

    public int getSpecialDef_bonus() {
        return specialDef_bonus;
    }

}
