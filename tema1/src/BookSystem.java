import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BookSystem {
    /* data */
    public Book[] sBooks; //System Books
    public Author[] sAuthors;
    public EditorialGroup[] sEditorialGroups;
    public Language[] sLanguages;
    public PublishingBrand[] sPublishingBrands;
    public Countries[] countries;
    public PublishingRetailer[] sPublishingRetailers;

    /* initializing data functions */
    public BookSystem(){}

    public Book[] readBooks(Language[] lgs){
        /* books */
        Book[] books = new Book[0];
        String file = "init/books.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                Book[] aux = new Book[books.length + 1];
                System.arraycopy(books, 0, aux, 0, books.length);
                books = aux;
                String[] words = line.split("###", 8);
                int lid = Integer.parseInt(words[6]);
                Language language = null;
                for(Language l : lgs){
                    if(l.getID() == lid){
                        language = l;
                        break;
                    }
                }
                books[books.length - 1] = new Book(words[0], words[1], words[2],
                        words[3], words[4], words[5], language, words[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Author[] readAuthors(){
        /* authors */
        Author[] authors = new Author[0];
        String file = "init/authors.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                Author[] aux = new Author[authors.length + 1];
                System.arraycopy(authors, 0, aux, 0, authors.length);
                authors = aux;
                String[] words = line.split("###", 3);
                authors[authors.length - 1] = new Author(words[0], words[1], words[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Language[] readLanguages(){
        /* languages */
        Language[] languages = new Language[0];
        String file = "init/languages.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                Language[] aux = new Language[languages.length + 1];
                System.arraycopy(languages, 0, aux, 0, languages.length);
                languages = aux;
                String[] words = line.split("###", 3);
                languages[languages.length - 1] = new Language(words[0], words[1], words[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return languages;
    }

    public void linkBooksAuthors(Book[] books, Author[] authors){
        /* books-authors */
        String file = "init/books-authors.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(Book b : books){
                    if(b.getID() == Long.parseLong(words[0])){
                        for(Author a : authors){
                            if(a.getID() == Long.parseLong(words[1])){
                                b.addAuthor(a);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EditorialGroup[] readEditorialGroups(){
        /* editorial-groups */
        EditorialGroup[] editorialGroups = new EditorialGroup[0];
        String file = "init/editorial-groups.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                EditorialGroup[] aux = new EditorialGroup[editorialGroups.length + 1];
                System.arraycopy(editorialGroups, 0, aux, 0, editorialGroups.length);
                editorialGroups = aux;
                String[] words = line.split("###", 2);
                editorialGroups[editorialGroups.length - 1] = new EditorialGroup(words[0], words[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editorialGroups;
    }

    public PublishingBrand[] readPublishingBrand(){
        /* publishing-brands */
        PublishingBrand[] publishingBrands = new PublishingBrand[0];
        String file = "init/publishing-brands.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                PublishingBrand[] aux = new PublishingBrand[publishingBrands.length + 1];
                System.arraycopy(publishingBrands, 0, aux, 0, publishingBrands.length);
                publishingBrands = aux;
                String[] words = line.split("###", 2);
                publishingBrands[publishingBrands.length - 1] = new PublishingBrand(words[0], words[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publishingBrands;
    }

    public void linkEGroupsBooks(EditorialGroup[] groups, Book[] books){
        /* editorial-groups-books */
        String file = "init/editorial-groups-books.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(EditorialGroup g : groups){
                    if(g.getID() == Long.parseLong(words[0])){
                        for(Book b : books){
                            if(b.getID() == Long.parseLong(words[1])){
                                g.addBook(b);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkPBrandsBooks(PublishingBrand[] brands, Book[] books){
        /* publishing-brands-books */
        String file = "init/publishing-brands-books.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(PublishingBrand p : brands){
                    if(p.getID() == Long.parseLong(words[0])){
                        for(Book b : books){
                            if(b.getID() == Long.parseLong(words[1])){
                                p.addBook(b);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Countries[] readCountries(){
        /* countries */
        Countries[] countries = new Countries[0];
        String file = "init/countries.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                Countries[] aux = new Countries[countries.length + 1];
                System.arraycopy(countries, 0, aux, 0,countries.length);
                countries = aux;
                String[] words = line.split("###", 2);
                countries[countries.length - 1] = new Countries(words[0], words[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;

    }

    public PublishingRetailer[] readPublishRetailers(){
        /* publishing-retailers */
        PublishingRetailer[] pubRetailers = new PublishingRetailer[0];
        String file = "init/publishing-retailers.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                PublishingRetailer[] aux = new PublishingRetailer[pubRetailers.length + 1];
                System.arraycopy(pubRetailers, 0, aux, 0, pubRetailers.length);
                pubRetailers = aux;
                String[] words = line.split("###", 2);
                pubRetailers[pubRetailers.length - 1] = new PublishingRetailer(words[0], words[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pubRetailers;
    }

    public void linkPRetailerCountries(PublishingRetailer[] pubR, Countries[] countries){
        /* publishing-retailers-countries */
        String file = "init/publishing-retailers-countries.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(PublishingRetailer p : pubR){
                    if(p.getID() == Long.parseLong(words[0])){
                        for(Countries c : countries){
                            if(c.getID() == Long.parseLong(words[1])){
                                p.addCountries(c);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkPRetailerBooks(PublishingRetailer[] pubR, Book[] books){
        /* publishing-retailers-books */
        String file = "init/publishing-retailers-books.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(PublishingRetailer p : pubR){
                    if(p.getID() == Long.parseLong(words[0])){
                        for(Book b : books){
                            if(b.getID() == Long.parseLong(words[1])){
                                p.addPubArtifact(b);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkPRetailerGroups(PublishingRetailer[] pubR, EditorialGroup[] groups){
        /* publishing-retailers-editorial-groups */
        String file = "init/publishing-retailers-editorial-groups.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(PublishingRetailer p : pubR){
                    if(p.getID() == Long.parseLong(words[0])){
                        for(EditorialGroup g : groups){
                            if(g.getID() == Long.parseLong(words[1])){
                                p.addPubArtifact(g);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkPRetailerBrands(PublishingRetailer[] pubR, PublishingBrand[] brands){
        /* publishing-retailers-publishing-brands */
        String file = "init/publishing-retailers-publishing-brands.in";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /* citire header fisier */
            String line = br.readLine();
            /* citire line cu linie */
            while ((line = br.readLine()) != null) {
                String[] words = line.split("###", 2);
                for(PublishingRetailer p : pubR){
                    if(p.getID() == Integer.parseInt(words[0])){
                        for(PublishingBrand b : brands){
                            if(b.getID() == Integer.parseInt(words[1])){
                                p.addPubArtifact(b);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* functionality functions */
    public Book[] getBooksForPublishingRetailerID(int publishingRetailerID){
        Book[] bookList = new Book[0];
        PublishingRetailer pub = null;
        /* finding the retailer */
        for(PublishingRetailer p : this.sPublishingRetailers){
            if(p.getID() == publishingRetailerID){
                pub = p;
                break;
            }
        }

        /* searching and adding the books to list */
        IPublishingArtifact[] list = pub.getPublishingArtifacts();
        for(int i = 0; i < list.length; i++){
            Book[] books = new Book[0];
            /* extract list of books to be checked */
            if(list[i] instanceof Book){
                books = new Book[1];
                books[0] = (Book)list[i];
            } else if(list[i] instanceof PublishingBrand){
                books = ((PublishingBrand)list[i]).getBooks();
            } else if(list[i] instanceof EditorialGroup){
                books = ((EditorialGroup)list[i]).getBooks();
            }

            /* check all books found */
            for(Book b : books){
                boolean add = true;
                for(Book bL : bookList){
                    if(b.equals(bL)){
                        add = false;
                        break;
                    }
                }
                if(add){
                    Book[] aux = new Book[bookList.length + 1];
                    System.arraycopy(bookList, 0, aux, 0, bookList.length);
                    bookList = aux;
                    bookList[bookList.length - 1] = b;
                }
            }
        }
        return bookList;
    }

    public Language[] getsLanguagesForPublishingRetailerID(int publishingRetailerID){
        Language[] lgList = new Language[0];

        Book[] books = this.getBooksForPublishingRetailerID(publishingRetailerID);
        /* check all books found */
        for(Book b : books){
            boolean add = true;
            for(Language l : lgList){
                if(l.equals(b.getLanguage())){
                    add = false;
                    break;
                }
            }
            if(add){
                Language[] aux = new Language[lgList.length + 1];
                System.arraycopy(lgList, 0, aux, 0, lgList.length);
                lgList = aux;
                lgList[lgList.length - 1] = b.getLanguage();
            }
        }

        return lgList;
    }

    public Countries[] getCountriesForBookID(int bookID){
        Countries[] countries = new Countries[0];

        for(PublishingRetailer retailer : this.sPublishingRetailers){
            for(IPublishingArtifact artifact : retailer.getPublishingArtifacts()){
                /* daca intr-un artifact al retailerului am gasit cartea, ii se atribuie toate tarile
                * nici o tara noua nu va fi adaugata la lista daca se mai gaseste cartea intr-un alt artifact
                * asa ca oprim cautarea in retailer */
                boolean stopSearch = false;
                if(artifact instanceof Book && ((Book) artifact).getID() == bookID){
                    countries = addCountries(retailer, countries);
                    stopSearch = true;
                } else if(artifact instanceof PublishingBrand){
                    for(Book b : ((PublishingBrand) artifact).getBooks()){
                        if(b.getID() == bookID){
                            countries = addCountries(retailer, countries);
                           stopSearch = true;
                           break;
                        }
                    }
                } else if(artifact instanceof  EditorialGroup){
                    for(Book b : ((EditorialGroup) artifact).getBooks()){
                        if(b.getID() == bookID){
                            countries = addCountries(retailer, countries);
                            stopSearch = true;
                            break;
                        }
                    }
                }

                if(stopSearch)
                    break;
            }
        }

        return countries;
    }

    public Book[] getCommonBooksForRetailerIDs(int retailerID1, int retailerID2){
        Book[] books1 = getBooksForPublishingRetailerID(retailerID1);
        Book[] books2 = getBooksForPublishingRetailerID(retailerID2);

        Book[] books = new Book[0];

        for(Book b1 : books1){
            for(Book b2 : books2){
                if(b1.equals(b2)){
                    Book[] aux = new Book[books.length + 1];
                    System.arraycopy(books, 0, aux, 0, books.length);
                    books = aux;
                    books[books.length - 1] = b1;
                }
            }
        }

        return books;
    }

    public Book[] getAllBooksForRetailerIDs (int retailerID1, int retailerID2){
        Book[] books1 = getBooksForPublishingRetailerID(retailerID1);
        Book[] books2 = getBooksForPublishingRetailerID(retailerID2);

        Book[] books = new Book[books2.length];
        System.arraycopy(books2, 0, books, 0, books.length);

        for(Book b1 : books1){
            boolean add = true;
            for(Book b2 : books){
                if(b1.equals(b2)){
                    add = false;
                }
            }
            if(add){
                Book[] aux = new Book[books.length + 1];
                System.arraycopy(books, 0, aux, 0, books.length);
                books = aux;
                books[books.length - 1] = b1;
            }
        }

        return books;
    }

    /* functii auxiliare */
    static Countries[] addCountries(PublishingRetailer pub, Countries[] countries){
        if(countries.length == 0){
            Countries[] pubCountries = pub.getCountries();
            Countries[] aux = new Countries[pubCountries.length];
            System.arraycopy(pubCountries, 0, aux, 0, pubCountries.length);
            countries = aux;
        } else{
            for(Countries country : pub.getCountries()) {
                boolean add = true;
                for (Countries c : countries) {
                    if (c.equals(country)){
                        add = false;
                    }
                }
                if(add){
                    Countries[] aux = new Countries[1 + countries.length];
                    System.arraycopy(countries, 0, aux, 0, countries.length);
                    countries = aux;
                    countries[countries.length - 1] = country;
                }
            }
        }

        return countries;
    }

}
