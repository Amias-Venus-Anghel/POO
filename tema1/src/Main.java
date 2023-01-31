import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class Main {
    public static void main(String[] args) {

        /* initialization */
        BookSystem sst =  new BookSystem();
        sst.sLanguages = sst.readLanguages();
        sst.countries = sst.readCountries();
        sst.sAuthors = sst.readAuthors();
        sst.sBooks = sst.readBooks(sst.sLanguages);
        sst.sEditorialGroups = sst.readEditorialGroups();
        sst.sPublishingBrands = sst.readPublishingBrand();
        sst.sPublishingRetailers =  sst.readPublishRetailers();
        /* linking data */
        sst.linkBooksAuthors(sst.sBooks, sst.sAuthors);
        sst.linkEGroupsBooks(sst.sEditorialGroups, sst.sBooks);
        sst.linkPBrandsBooks(sst.sPublishingBrands, sst.sBooks);
        sst.linkPRetailerCountries(sst.sPublishingRetailers, sst.countries);
        sst.linkPRetailerBooks(sst.sPublishingRetailers, sst.sBooks);
        sst.linkPRetailerGroups(sst.sPublishingRetailers, sst.sEditorialGroups);
        sst.linkPRetailerBrands(sst.sPublishingRetailers, sst.sPublishingBrands);

        /* tests */
        testing(1, 2, sst);
        testing(2, 3, sst);
        testing(3, 4, sst);
        testing(4, 5, sst);
        testing(5, 6, sst);


    }

    /* metoda de apelare a celor 5 metode implementate */
    static void testing(int id1, int id2, BookSystem sst){

        System.out.println();

        Book[] booksID1 = sst.getBooksForPublishingRetailerID(id1);
        System.out.println("Book IDs for Retailer id " + id1);
        for(Book b : booksID1){
            System.out.print(b.getID() + " ");
        }
        System.out.println();
        System.out.println();

        Language[] languagesID1 = sst.getsLanguagesForPublishingRetailerID(id1);
        System.out.println("Languages for Retailer id " + id1);
        for(Language l : languagesID1){
            System.out.println(l.toString());
        }
        System.out.println();
        System.out.println();

        /* testez pentru prima carte din vectorul de carti ale retailerului */
        Countries[] countriesID1 = sst.getCountriesForBookID(booksID1[0].getID());
        System.out.println("Countries for book id " + booksID1[0].getID());
        for(Countries c : countriesID1){
            System.out.println(c.toString());
        }
        System.out.println();
        System.out.println();

        Book[] allBooks = sst.getAllBooksForRetailerIDs(id1, id2);
        System.out.println("All books ids for retailers ids " + id1 + " and " + id2);
        for(Book b : allBooks){
            System.out.print(b.getID() + " ");
        }
        System.out.println();
        System.out.println();

        Book[] commonBooks = sst.getCommonBooksForRetailerIDs(id1, id2);
        System.out.println("Common books ids for retailers ids " + id1 + " and " + id2);
        for(Book b : commonBooks){
            System.out.print(b.getID() + " ");
        }
        System.out.println();
        System.out.println();
    }
}