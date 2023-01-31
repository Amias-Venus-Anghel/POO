import java.util.BitSet;

public class EditorialGroup implements IPublishingArtifact{
    private int ID;
    private String name;
    private Book[] books = new Book[0];

    public EditorialGroup(String id, String n){
        this.ID = Integer.parseInt(id);
        this.name = n;
    }

    public EditorialGroup(){}

    public int getID(){
        return this.ID;
    }

    public void addBook(Book b){
        Book[] aux = new Book[this.books.length + 1];
        System.arraycopy(this.books, 0, aux, 0, this.books.length);
        this.books = aux;
        this.books[this.books.length - 1] = b;
    }

    public Book[] getBooks(){
        return this.books;
    }
    @Override
    public String Publish() {
        String publish = "<xml>\n" +
                "   <editorialGroup>\n" +
                "       <ID>" + this.ID + "</ID>\n" +
                "       <Name>" + this.name + "</Name>\n" +
                "   </editorialGroup>\n" +
                "   <books>\n";
        /* adaugarea stringului corespunzator pentru fiecare carte din lista */
        if(this.books != null)
        for(Book b : this.books){
            publish += "        <book>\n";
            publish += b.details(1); //tabuu
            publish += "        </book>\n";
        }
        publish += "   </books>\n" +
                "</xml>\n";
        return publish;
    }
}
