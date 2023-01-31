package translator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import translator.pojos.Definition;
import translator.pojos.MessengerWord;
import translator.pojos.Word;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Dictionary {
    private ArrayList<Word> words = new ArrayList<>();

    public Dictionary() {
    }

    public Dictionary(ArrayList<Word> words) {
        this.words = words;
    }

    public ArrayList<Word> getWords() {
        return this.words;
    }

    /**
     * Adds or updates a word into the list of words.
     * <p>
     * If the word need to be updated, the method returns true only if new
     * information has been added
     *
     * @param word word to be added
     * @return true for successful adding or updating and false otherwise
     */
    public boolean addEntry(Word word) {
        /* check if word is already in the list */
        if (!this.words.contains(word)) {
            /* check if word exists but needs update */
            Word w = this.getWord(word.getWord(), false, null);
            if (w == null) {
                this.words.add(word);
                return true;
            } else {
                boolean updated = false;
                if (word.getDefinitions() != null)
                    for (Definition d : word.getDefinitions()) {
                        if (this.updateDefinition(w, d))
                            updated = true;
                    }
                return updated;
            }
        } else return false;
    }

    /**
     * Returns the searched word and builds a messenger word if necessary.
     * <p>
     * Searches in the list of word for the word and returns is. If necessary
     * builds a messenger word containing information necessary for translation.
     * <p>
     * Returns null if the word is not found.
     *
     * @param word          word to be searched
     * @param makeMessenger is true if a messenger word has to be build; false
     *                      otherwise
     * @param mw            messenger word to be built
     * @return the searched word if found or null otherwise
     */
    private Word getWord(String word, boolean makeMessenger, MessengerWord mw) {
        for (Word w : this.words) {
            if (w.getType().equals("verb")) {
                /* verb */
                /* search the verb person */
                for (int i = 0; i < 3; i++) {
                    if (w.getSingular().get(i).equals(word)) {
                        if (makeMessenger) {
                            mw.setEn(w.getWord_en());
                            mw.setType(w.getType());
                            mw.setSingular(true);
                            mw.setVerbPers(i);
                        }
                        return w;
                    }
                    if (w.getPlural().get(i).equals(word)) {
                        if (makeMessenger) {
                            mw.setEn(w.getWord_en());
                            mw.setType(w.getType());
                            mw.setSingular(false);
                            mw.setVerbPers(i);
                        }
                        return w;
                    }
                }
                /* check if searched word is the base form of the verb */
                if (w.getWord().equals(word)) {
                    if (makeMessenger) {
                        mw.setEn(w.getWord_en());
                        mw.setType(w.getType());
                        mw.setSingular(true);
                        /* for base word, verbPers gets value 3 */
                        mw.setVerbPers(3);
                    }
                    return w;
                }
            } else {
                /* noun */
                /* check if searched word is the base form of the noun */
                if (w.getWord().equals(word)) {
                    if (makeMessenger) {
                        mw.setEn(w.getWord_en());
                        mw.setType(w.getType());
                        mw.setSingular(true);
                    }
                    return w;
                }
                /* check singular and plural forms of the noun */
                if (w.getSingular().get(0).equals(word)) {
                    if (makeMessenger) {
                        mw.setEn(w.getWord_en());
                        mw.setType(w.getType());
                        mw.setSingular(true);
                    }
                    return w;
                }
                if (w.getPlural().get(0).equals(word)) {
                    if (makeMessenger) {
                        mw.setEn(w.getWord_en());
                        mw.setType(w.getType());
                        mw.setSingular(false);
                    }
                    return w;
                }
            }
        }
        /* word was not found */
        return null;
    }

    /**
     * Updates the definitions of a given word.
     * <p>
     * If the given definition does not exist, it's added; if the definition
     * already exists but has additional content, the definition is updated,
     * otherwise the definition is not added
     *
     * @param word       word to have definition updated
     * @param definition definition to be updated
     * @return true if the definition was updated or added, false otherwise
     */
    private boolean updateDefinition(Word word, Definition definition) {
        /* check if word has any definitions and create the list otherwise */
        if (word.getDefinitions() == null)
            word.setDefinitions(new ArrayList<>());
        /* check if definition already exists */
        for (Definition d : word.getDefinitions()) {
            if (d.equals(definition))
                return false;
            /* check if the definition needs to be updated */
            if (d.getDict().equals(definition.getDict()) &&
                    d.getDictType().equals(definition.getDictType()) &&
                    d.getYear() == definition.getYear()) {
                boolean added = false;
                for (String text : definition.getText()) {
                    if (!d.getText().contains(text)) {
                        d.getText().add(text);
                        added = true;
                    }
                }
                if (added)
                    return true;
            }
        }
        /* adding definition to list */
        word.getDefinitions().add(definition);
        return true;
    }

    /**
     * Adds or updates a definition for a word.
     * <p>
     * Throws an exception if the word does not exist in the list.
     *
     * @param word       word to have definition added to
     * @param definition definition to be added
     * @return true for successful adding, false otherwise
     */
    public boolean addDefinition(String word, Definition definition) {
        Word found = getWord(word, false, null);
        if (found != null) {
            return this.updateDefinition(found, definition);
        }
        /* word not found */
        throw new ExceptionMissingEntry();
    }

    /**
     * Removes given definition from given word.
     * <p>
     * Throws an exception if the word does not exist in the list of words.
     * <p>
     * Identifying code for definition has the following format:
     * "dictionary name###dictionary type###year of publication".
     * If the identifying code does not have a valid structure, the method
     * returns false.
     *
     * @param word          word to have definition removed
     * @param dictinaryCode identifying code for the definition to be removed
     * @return true if removal is successful, false otherwise
     */
    //redo with hashed
    public boolean removeDef(String word, String dictinaryCode) {
        /* get code information & check valid code */
        String[] codeInfo = dictinaryCode.split("###");
        if (codeInfo.length != 3)
            return false;

        /* get word */
        Word found = getWord(word, false, null);

        if (found != null) {
            /* no definition to remove */
            if (found.getDefinitions() == null)
                return false;

            for (Definition d : found.getDefinitions()) {
                /* check if the information from the  dictionaryCode match the
                 * actual definition*/
                if (d.getDict().equals(codeInfo[0]) &&
                        d.getDictType().equals(codeInfo[1]) &&
                        d.getYear() == Integer.parseInt(codeInfo[2])) {

                    found.getDefinitions().remove(d);
                    return true;
                }
            }
            /* definition not found */
            return false;
        }
        /* word not found */
        throw new ExceptionMissingEntry();
    }

    /**
     * Gets a list of definitions for a given word.
     * <p>
     * Throws an exception if the word does not exist in the words list.
     *
     * @param word word to get definitions for
     * @return an arraylist of definitions
     */
    public ArrayList<Definition> getWordDefinitions(String word) {
        Word found = getWord(word, false, null);
        if (found != null)
            return found.getDefinitions();
        /* word not found */
        throw new ExceptionMissingEntry();
    }

    /**
     * Finds a word's translation information.
     * <p>
     * Creates a messenger object with the information needed for translating the
     * word to another language.
     * Throws an exception if the word does not exist in the list of words.
     *
     * @param word word to be translated
     * @return a messenger containing information needed for translation about
     * the word
     */
    public MessengerWord translateLgToEn(String word) {
        MessengerWord mw = new MessengerWord();
        Word found = getWord(word, true, mw);
        if (found != null)
            return mw;
        /* word not found */
        throw new ExceptionMissingEntry();
    }

    /**
     * Gives translation of a word based on translation information.
     * <p>
     * Throws an exception if the word does not exist in the words list.
     *
     * @param mw messenger object containing information about the word
     * @return translation of the word
     */
    public String translateEnToLg(MessengerWord mw) {
        /* search information from messenger word */
        for (Word w : this.words) {
            String word_en = w.getWord_en();
            /* check type */
            if (mw.getType().equals(w.getType())) {
                /* noun */
                if (mw.getType().equals("noun")) {
                    if (mw.isSingular() && mw.getEn().equals(word_en))
                        return w.getSingular().get(0);
                    if (!mw.isSingular() && mw.getEn().equals(word_en))
                        return w.getPlural().get(0);
                } else {
                    /* verb */
                    if ((mw.getVerbPers() == 3) && mw.getEn().equals(word_en))
                        return w.getWord();
                    if (mw.isSingular() && mw.getEn().equals(word_en))
                        return w.getSingular().get(mw.getVerbPers());
                    if (!mw.isSingular() && mw.getEn().equals(word_en))
                        return w.getPlural().get(mw.getVerbPers());

                }
            }
        }
        /* word not found */
        throw new ExceptionMissingEntry();
    }

    /**
     * Removes a word from the list of words.
     * <p>
     * Throws an exception if the word does not exist in the list of words.
     *
     * @param word word to be removed
     */
    public void removeEntry(String word) {
        if (!this.words.removeIf(w -> w.getWord().equals(word)))
            throw new ExceptionMissingEntry();
    }

    /**
     * Returns a list of synonyms for a word.
     * <p>
     * Throws an exception if the word does not exist in the list of words.
     *
     * @param word word to get synonyms for
     * @return a list of synonyms for the word
     */
    public ArrayList<String> getSynonyms(String word) {
        Word found = getWord(word, false, null);

        if (found != null) {
            /* for every definition containing synonyms
             * adds all synonyms to the list to be returned */
            ArrayList<String> synonyms = new ArrayList<>();
            for (Definition d : found.getDefinitions()) {
                if (d.getDictType().equals("synonyms"))
                    synonyms.addAll(d.getText());
            }
            return synonyms;
        }

        /* word not found */
        throw new ExceptionMissingEntry();
    }

    /**
     * Imports a list of words from a json file.
     *
     * @param path path to file
     * @return a list of words
     * @throws IOException
     */
    public static Dictionary importDictionary(String path) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(path));

        /* reads input data from path into an array of words */
        ArrayList<Word> words = gson.fromJson(reader,
                new TypeToken<List<Word>>() {}.getType());
        Dictionary dic = new Dictionary(words);

        reader.close();
        return dic;
    }

    /**
     * Exports a list of words to a json file.
     * <p>
     * Creates a file named "lg_expDict.json", where lg is the language of the
     * words. The file is created in the "exported" folder; if this folder
     * doesn't exist, it's created. The exported file contains alphabetically
     * sorted words and for every word its definitions are sorted by year.
     *
     * @param dictionary list of words to be exported
     * @param language   language of the words
     * @return a json file
     * @throws IOException
     */
    public static void exportDictionary(Dictionary dictionary, String language)
            throws IOException {
        /* sorts words */
        Collections.sort(dictionary.getWords());
        for (Word w : dictionary.getWords()) {
            if (w.getDefinitions() != null)
                /* sorts definitions */
                Collections.sort(w.getDefinitions());
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String output = gson.toJson(dictionary.getWords());
        /* creates export folder if it does not exist */
        File folder = new File("exported");
        if (!folder.exists())
            folder.mkdir();
        /* creates and writes information in new file */
        File file = new File("exported/" + language + "_expDict.json");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(output);
        writer.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictionary that = (Dictionary) o;
        return Objects.equals(words, that.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "words=" + words +
                '}';
    }
}

