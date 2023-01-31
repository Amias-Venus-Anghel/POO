package project_pokemon;

import org.junit.jupiter.api.Test;
import project_pokemon.battle_arena.Arena;

public class TestArena {

    public Arena getArena() {
        String path = "src/main/resources/pokemon_trainers/trainers_1.json";
        return new Arena(path);
    }

    @Test
    public void testDuel() {
        Arena arena = getArena();
        arena.adventure(false);
    }

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            System.out.println("i " + i);
            for (int j = i; j < 5; j++) {
                if (j == 3)
                    break;
                System.out.println("j " + j);
            }
        }
    }

}
