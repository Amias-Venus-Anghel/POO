public class Author {
    private int ID;
    private String firstName;
    private String lastName;

    public Author(String id, String fN, String lN){
        this.ID = Integer.parseInt(id);
        this.firstName = fN;
        this.lastName = lN;
    }

    public Author(){}

    public int getID() {
        return this.ID;
    }

    @Override
    public String toString() {
        return "" + firstName + " " + lastName + "";
    }

}
