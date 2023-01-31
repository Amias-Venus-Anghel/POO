import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book implements IPublishingArtifact{
    private int ID;
    private String name;
    private String subtitle;
    public String ISBN;
    private int pageCount;
    private String keyWords;
    private Language languageID;
    private Date createdOn;
    private Author[] authors = new Author[0];

    public Book(){}
    public Book(String id, String n, String s, String isbn, String pC, String kW, Language lg, String c){
        this.ID = Integer.parseInt(id);
        this.name = n;
        this.subtitle = s;
        this.ISBN = isbn;
        this.pageCount = Integer.parseInt(pC);
        this.keyWords = kW;
        this.languageID = lg;
        try {
            this.createdOn = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getID(){
        return this.ID;
    }

    public Language getLanguage() {
        return languageID;
    }

    public void addAuthor(Author a){
        Author[] aux = new Author[1 + this.authors.length];
        System.arraycopy(this.authors, 0, aux, 0, this.authors.length);
        this.authors = aux;
        this.authors[this.authors.length - 1] = a;
    }

    public String details(int tabs){
        String authorsList = this.authors[0].toString();
        for(int i = 1; i < this.authors.length; i++){
            authorsList += "," + this.authors[i].toString();
        }

        /* tabs e 0 daca nu avem taburi suplimentare, 1 daca avem */
        String tab = "";
        if(tabs == 1)
            tab = "         ";

        return tab + "   <title>" + this.name + "</title>\n" +
                tab + "   <subtitle>" + this.subtitle + "</subtitle>\n" +
                tab + "   <isbn>" + this.ISBN + "</isbn>\n" +
                tab + "   <pageCount>" + this.pageCount + "1</pageCount>\n" +
                tab + "   <keywords>" + this.keyWords + "</keywords>\n" +
                tab + "   <languageID>" + this.languageID.getID() + "</languageID>\n" +
                tab + "   <createdOn>" + this.createdOn + "<createdOn>\n" +
                tab + "   <authors>" + authorsList + "<authors>\n" ;

    }

    @Override
    public String Publish() {

        return   "<xml>\n" +
                this.details(0) +
                "</xml>";

    }
}
