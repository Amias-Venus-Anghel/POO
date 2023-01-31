package translator;

import translator.pojos.Definition;
import translator.pojos.MessengerWord;
import translator.pojos.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class Translator {
    private HashMap<String, Dictionary> translator = new HashMap<>();

    public Translator() {
    }

    public HashMap<String, Dictionary> getTranslator() {
        return translator;
    }

    public void setTranslator(HashMap<String, Dictionary> translator) {
        this.translator = translator;
    }

    public void addDictionary(Dictionary dic, String language) {
        this.translator.put(language, dic);
    }

    /**
     * Adds a word to the translator.
     * <p>
     * Throws an exception if the language does not exist in the translator.
     *
     * @param word     word to be included
     * @param language the language of the word
     * @return true for successfully added and false if the word already exists
     */
    public boolean addWord(Word word, String language) {
        if (!this.translator.containsKey(language))
            throw new ExceptionMissingEntry();
        Dictionary dic = this.translator.get(language);
        return dic.addEntry(word);
    }

    /**
     * Removes a word from the translator.
     * <p>
     * Throws an exception if the language does not exist in the translator.
     * If the word does not exist in the translator, it's considered a failed
     * removal.
     *
     * @param word     word to be removed
     * @param language the language of the word
     * @return true for successfully removed and false otherwise
     */
    public boolean removeWord(String word, String language) {
        if (!this.translator.containsKey(language))
            throw new ExceptionMissingEntry();
        Dictionary dic = this.translator.get(language);
        /* removeEntry throws an exception if the word does not exist
         * catches exception and returns corresponding result */
        try {
            dic.removeEntry(word);
            return true;
        } catch (ExceptionMissingEntry e) {
            return false;
        }
    }

    /**
     * Translates a word from a given language to another.
     * <p>
     * Throws an exception if either of the languages or the word do not exist
     * in the translator.
     *
     * @param word         word to be translated
     * @param fromLanguage initial language of the word
     * @param toLanguage   language to be translated to
     * @return the translation of the word
     */
    public String translateWord(String word, String fromLanguage,
                                String toLanguage) {
        /* checks languages */
        if (!this.translator.containsKey(fromLanguage))
            throw new ExceptionMissingEntry();
        if (!this.translator.containsKey(toLanguage))
            throw new ExceptionMissingEntry();
        /* get dictionaries */
        Dictionary fromDic = this.translator.get(fromLanguage);
        Dictionary toDic = this.translator.get(toLanguage);
        /* gets mw with information for translation and sends it to be
        translated */
        MessengerWord mw = fromDic.translateLgToEn(word);
        return toDic.translateEnToLg(mw);
    }

    /**
     * Adds a new definition for a given word.
     * <p>
     * If the definition is an updated version for an existing definition, then
     * the definition of the word is updated.
     * Throws an exception if the language of the word or the word do not exist
     * in the translator.
     *
     * @param word       word to have added a new definition to
     * @param language   language of the word
     * @param definition definition to be added
     * @return true for successful adding and false otherwise
     */
    public boolean addDefinitionForWord(String word, String language,
                                        Definition definition) {
        /* checks language */
        if (!this.translator.containsKey(language))
            throw new ExceptionMissingEntry();
        return this.translator.get(language).addDefinition(word, definition);
    }

    /**
     * Removes a definition.
     *
     * @param word       word to have a definition removed from
     * @param language   language of the word
     * @param dictionary identifying code for a dictionary
     * @return true for successful removal, false otherwise
     */
    public boolean removeDefinition(String word, String language,
                                    String dictionary) {
        /* checks language */
        if (!this.translator.containsKey(language))
            throw new ExceptionMissingEntry();
        Dictionary dic = this.translator.get(language);
        return dic.removeDef(word, dictionary);
    }

    /**
     * Returns a list of definitions for a given word.
     * <p>
     * Throws an exception if the language or word do not exist in the
     * translator.
     *
     * @param word     word who's definitions are desired
     * @param language language of the word
     * @return a list of definitions for the given word
     */
    public ArrayList<Definition> getDefinitionsForWord(String word,
                                                       String language) {
        /* checks language */
        if (!this.translator.containsKey(language))
            throw new ExceptionMissingEntry();
        ArrayList<Definition> definitions =
                this.translator.get(language).getWordDefinitions(word);
        /* sorting the definitions by the publishing year */
        Collections.sort(definitions);
        return definitions;
    }

    /**
     * Translates a given sentence from one language to another.
     * <p>
     * If a word does not have a translation in the desired language, the word
     * will remain in the initial language.
     * Throws an exception if the languages do not exist in the translator.
     *
     * @param sentence     sentence to be translated
     * @param fromLanguage initial language of the sentence
     * @param toLanguage   language to be translated to
     * @return translated sentence
     */
    public String translateSentence(String sentence, String fromLanguage,
                                    String toLanguage) {

        /* checks languages */
        if (!this.translator.containsKey(fromLanguage))
            throw new ExceptionMissingEntry();
        if (!this.translator.containsKey(toLanguage))
            throw new ExceptionMissingEntry();

        /* splitting the sentence in words */
        String[] words = sentence.split(" ");
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        /* building the sentence */
        for (String w : words) {
            sb.append(prefix);
            String translation;
            /* getting translation for each word
             * a word not found throws an exception
             * if exception is caught the word stays in the initial language */
            try {
                translation = this.translateWord(w, fromLanguage, toLanguage);
            } catch (ExceptionMissingEntry e) {
                translation = w;
            }
            sb.append(translation);
            prefix = " ";
        }
        return sb.toString();
    }

    /**
     * Builds 3 alternative translations for a given set of words.
     * <p>
     * The resulting sentences are stored in the translations ArrayList.
     * If a word does not have a translation to the desired language, it will
     * stay in the initial language.
     *
     * @param words        list of words to be translated
     * @param index        index of the current word to be translated
     * @param toLg         language to be translated to
     * @param fromLg       initial language of the words
     * @param translations list of translations built
     * @param sentence     partial built sentence
     */
    private void buildSentence(String[] words, int index, String toLg,
                               String fromLg, ArrayList<String> translations,
                               String sentence) {
        /* check if the number of desired translation has been reached */
        if (translations.size() == 3)
            return;
        /* check if sentence is ready to be saved */
        if (index == words.length) {
            sentence = sentence.trim();
            translations.add(sentence);
            return;
        }

        /* building the partial sentence */
        StringBuilder sb = new StringBuilder();
        try {
            /* finds basic translation of word and adds it to sentence */
            String translation = this.translateWord(words[index], fromLg, toLg);
            sb.append(sentence).append(translation).append(" ");
            /* finds translation for next word */
            buildSentence(words, index + 1, toLg, fromLg, translations,
                    sb.toString());

            /* if the number of alternative translation is not reached
             * builds alternatives using the synonims of the word
             * (if it has any) */
            ArrayList<String> synonims =
                    this.translator.get(toLg).getSynonyms(translation);
            if (synonims != null) {
                for (int i = 0; i < 2 && (i + 1) <= synonims.size()
                        && translations.size() < 3; i++) {
                    sb.setLength(0);
                    sb.append(sentence).append(synonims.get(i)).append(" ");
                    buildSentence(words, index + 1, toLg, fromLg,
                            translations, sb.toString());
                }
            }
            /* if the word has no translation, remains in the initial language */
        } catch (ExceptionMissingEntry e) {
            sb.append(sentence).append(words[index]).append(" ");
            buildSentence(words, index + 1, toLg, fromLg, translations,
                    sb.toString());
        }
    }

    /**
     * Builds 3 alternative translations for a given sentence.
     * <p>
     * Throws an exception if the languages do not exist in the translator.
     *
     * @param sentence     sentence to be translated
     * @param fromLanguage initial language of the sentence
     * @param toLanguage   language to be translated to
     * @return a list of maximum 3 alternative translations for the sentence
     */
    public ArrayList<String> translateSentences(String sentence,
                                                String fromLanguage,
                                                String toLanguage) {
        /* checks languages */
        if (!this.translator.containsKey(fromLanguage))
            throw new ExceptionMissingEntry();
        if (!this.translator.containsKey(toLanguage))
            throw new ExceptionMissingEntry();

        /* gets array of words to be translated */
        String[] words = sentence.split(" ");
        ArrayList<String> translations = new ArrayList<>(3);
        buildSentence(words, 0, toLanguage, fromLanguage,
                translations, "");
        return translations;

    }

    /**
     * Imports a translator from a given entry folder.
     * <p>
     * Reads all the files in the entry folder. If the file is a json file, it
     * imports the data into the translator.
     * Throws an exception if the folder is empty.
     *
     * @param importFolderPath path to entry folder
     * @return a new translator
     * @throws IOException
     */
    public static Translator importTranslator(String importFolderPath)
            throws IOException {
        Translator t = new Translator();
        File importFolder = new File(importFolderPath);
        File[] files = importFolder.listFiles();

        /* check if folder is empty */
        if (files != null) {
            if (files.length == 0)
                throw new ExceptionMissingEntry();

            /* check each file */
            for (File fileEntry : files) {
                String fileName = fileEntry.getName();
                String[] args = fileName.split("[_.]");
                /* checks file extension and imports if is json*/
                if (args[args.length - 1].equals("json")) {
                    Dictionary dic = Dictionary.importDictionary(
                            importFolderPath + "\\" + fileName);
                    t.addDictionary(dic, args[0]);
                }
            }
            return t;
        }
        /* if file is null */
        throw new NullPointerException();
    }

    /**
     * Exports a dictionary for a given language.
     * <p>
     * Throws an exception if the language does not exist in the translator.
     * Creates a file named "lg_expDict.json", where lg is the language of the
     * words. The file is created in the "exported" folder; if this folder
     * doesn't exist, it's created. The exported file contains alphabetically
     * sorted words and for every word its definitions are sorted by year.
     *
     * @param language language to be exported
     * @throws IOException
     */
    public void exportDictionary(String language) throws IOException {
        /* checks language */
        if (!this.translator.containsKey(language))
            throw new ExceptionMissingEntry();
        Dictionary.exportDictionary(this.translator.get(language), language);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Translator that = (Translator) o;
        return Objects.equals(translator, that.translator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translator);
    }

    @Override
    public String toString() {
        return "Translator{" +
                "translator=" + translator +
                '}';
    }
}

