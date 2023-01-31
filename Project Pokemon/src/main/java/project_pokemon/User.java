package project_pokemon;

import project_pokemon.battle_arena.Arena;

import java.util.Scanner;

public class User {
    /**
     * User interface; gets the input file path and starts the adventure.
     * Has aesthetic purpose.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello and welcome to the POKEMON arena");
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 1; i++) {
            String answer = "no";
            String path = null;
            /* check if the path given is correct */
            while (answer.equals("no")) {
                System.out.println("Which trainers will battle today?");
                System.out.println("\tpath to file:");
                path = sc.nextLine();
                System.out.println("You wrote: " + path);
                System.out.println("Is that correct?");
                answer = getAnswer(sc);
            }

            System.out.println("Would you like to record " +
                    "the battle log in a file?");
            answer = getAnswer(sc);
            System.out.println("Got it! Simulating now..");
            new Arena(path).adventure(answer.equals("yes"));
            System.out.println("Simulation complete.");
            System.out.println("Would you like to simulate again?");
            answer = getAnswer(sc);
            /* resets i in order to rerun the method */
            if (answer.equals("yes"))
                i = -1;
        }
    }

    /**
     * Gets an answer from scanner input and checks that it's yes or no.
     * @param sc scanner
     * @return String, either "yes" or "no"
     */
    private static String getAnswer(Scanner sc) {
        System.out.println("\t(yes/no):");
        String answer = sc.nextLine();
        if (!answer.equals("yes") && !answer.equals("no"))
            answer = getAnswer(sc);
        return answer;
    }
}
