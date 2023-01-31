package project_pokemon.import_export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Export {
    private static int logNumber = 0;

    /**
     * Exports the log in a file.
     * The file is numbered corresponding the number of simulations run.
     * @param log string with the log
     */
    public static void exportLog(String log) {
        try {
            File folder = new File("battle_logs");
            if (!folder.exists())
                folder.mkdir();
            /* creates and writes information in new file */
            File file = new File("battle_logs/log_" + logNumber + ".txt");
            logNumber++;
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(log);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
