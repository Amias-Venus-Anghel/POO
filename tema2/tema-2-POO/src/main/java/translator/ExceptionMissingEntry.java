package translator;

public class ExceptionMissingEntry extends RuntimeException{
    public ExceptionMissingEntry(){
        super("Missing Entry");
        System.out.println("WARNING: the entry does not exist!");
    }
}
