package project_pokemon.logger;

public class LogBuilder {
    private int separatorCounter;
    private int actionNumber;
    private StringBuilder log = new StringBuilder();

    public LogBuilder() {
        separatorCounter = 0;
        actionNumber = 1;
    }

    /**
     * Resets the log builder to be reused for a new log.
     */
    public void resetLogger() {
        separatorCounter = 0;
        actionNumber = 1;
        log.setLength(0);
    }

    /**
     * Saves the data from a log to the general log.
     */
    public void saveData() {
        Logger.getLogger().saveToLog(this.toString());
        resetLogger();
    }

    public void registerComment(String info) {
       log.append(info);
    }

    /**
     * Saves the information in the current log.
     *
     * @param info information to be registered
     */
    public void register(String info) {
        /*
        1 pokemon 1 + separator " HP "
        2 HP + separator vs
        3 pokemon 2 + separator " HP "
        4 HP + separator \n
        5 action number + pokemon 1 + separator " "
        6 move + separator /
        7 pokemon 2 + separator " "
        8 move + separator \n
        9 separator a. + pokemon 1 status + separator \n
        10 separator b. + pokemon 2 status + separator \n
        11 reset to point 5 */
        switch (separatorCounter) {
            case 7:
            case 3:
                log.append(info + "\n");
                break;
            case 0:
                log.append("\t");
            case 2:
                log.append(info + " HP ");
                break;
            case 1:
                log.append(info + " VS ");
                break;
            case 10:
                separatorCounter = 4;
            case 4:
                log.append("\t\t" + actionNumber + " ");
                actionNumber++;
            case 6:
                log.append(info + " ");
                break;
            case 5:
                log.append(info + "/");
                break;
            case 8:
                log.append("\t\t\ta. " + info + "\n");
                break;
            case 9:
                log.append("\t\t\tb. " + info + "\n");
                break;
        }
        separatorCounter++;
    }

    @Override
    public String toString() {
        return log.toString();
    }

}
