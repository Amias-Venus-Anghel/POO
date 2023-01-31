public class Language {
    private int ID;
    private String code;
    private String name;

    public Language(String id, String c, String n){
        this.ID = Integer.parseInt(id);
        this.code = c;
        this.name = n;
    }

    public Language(){}

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Language{" +
                "ID=" + ID +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
