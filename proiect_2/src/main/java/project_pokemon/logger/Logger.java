package project_pokemon.logger;

import project_pokemon.import_export.Export;

public class Logger {
    private StringBuilder log = new StringBuilder();
    private static Logger uniqueLogger;
    private static boolean printInFile;

    /**
     * Keeps and returns the unique instance of the logger.
     * @return the logger
     */
    public static Logger getLogger() {
        if (uniqueLogger == null)
            uniqueLogger = new Logger();
        return uniqueLogger;
    }

    private Logger() {
    }

    /**
     * Saves data to general log
     * @param info data to be saved
     */
    public void saveToLog(String info){
        this.log.append(info + "\n");
    }

    /**
     * Prints the log at standard output or in file.
     */
    public void printLog() {
        if (printInFile) {
            Export.exportLog(log.toString());
        } else
            System.out.println(log.toString());
        log.setLength(0);
    }

    public void setPrintMode(boolean inFile) {
        printInFile = inFile;
    }
}
