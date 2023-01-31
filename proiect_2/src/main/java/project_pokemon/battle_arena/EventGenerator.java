package project_pokemon.battle_arena;

import java.util.Random;

public class EventGenerator {

    public static enum Event {
        Neutrel1, Neutrel2, Battle
    }

    public EventGenerator() {}

    /**
     * Generates a random event for the arena
     * @return a random event
     */
    public Event generateEvent() {
            int pick = new Random().nextInt(Event.values().length);
        return Event.values()[pick];
    }

}
