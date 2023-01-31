public class Countries {
    private int ID;
    private String countryCode;

    public Countries(String id, String cc){
        this.ID = Integer.parseInt(id);
        this.countryCode = cc;
    }

    public Countries(){}

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Country{" +
                "ID=" + ID +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
