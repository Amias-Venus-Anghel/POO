package translator;

import org.junit.jupiter.api.Test;
import translator.pojos.Definition;
import translator.pojos.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestTranslator {
    @Test
    public void testAddWordSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* entry build */
        ArrayList<String> Sg = new ArrayList<>();
        Sg.add("elefant");
        ArrayList<String> Pl = new ArrayList<>();
        Pl.add("elefanti");
        Word w1 = new Word("elefant", "elephant",
                "noun", Sg, Pl, null);

        /* add new word */
        assertTrue(t.addWord(w1, "ro"));

        /* entry build */
        ArrayList<String> sg = new ArrayList<>();
        sg.add("câine");
        ArrayList<String> pl = new ArrayList<>();
        pl.add("câini");
        ArrayList<String> text = new ArrayList<>();
        text.add("Definitie test");
        Definition def = new Definition("Dictionar test",
                "definitions", 2022, text);
        ArrayList<Definition> defs = new ArrayList<>();
        defs.add(def);
        Word w2 = new Word("câine", "dog", "noun",
                sg, pl, defs);

        /* update a word */
        assertTrue(t.addWord(w2, "ro"));
    }

    @Test
    public void testAddWordFail() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* get existent entry */
        Dictionary d = t.getTranslator().get("ro");
        Word word = d.getWords().get(0);
        /* normal fail, word already exists */
        assertFalse(t.addWord(word, "ro"));

        /* entry build */
        ArrayList<String> sg = new ArrayList<>();
        sg.add("câine");
        ArrayList<String> pl = new ArrayList<>();
        pl.add("câini");
        Word w2 = new Word("câine", "dog", "noun",
                sg, pl, null);

        /* different word, but no additional information to be updated */
        assertFalse(t.addWord(w2, "ro"));
    }

    @Test
    public void testRemoveWordSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* get existent entry */
        Dictionary d = t.getTranslator().get("ro");
        Word word = d.getWords().get(0);

        assertTrue(t.removeWord(word.getWord(), "ro"));
    }

    @Test
    public void testRemoveWordFailMissingWord() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        String word = "porc";
        /* word does not exist */
        assertFalse(t.removeWord(word, "ro"));
    }

    @Test
    public void testRemoveWordMissingLanguageThrowsException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* get existent entry */
        Dictionary d = t.getTranslator().get("ro");
        Word word = d.getWords().get(0);

        Exception e = assertThrows(ExceptionMissingEntry.class,
                () -> t.removeWord(word.getWord(), "it"));
        String expectedMessage = "Missing Entry";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTranslateWordSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        assertEquals("chat", t.translateWord("pisică", "ro", "fr"));
        assertEquals("chats", t.translateWord("pisici", "ro", "fr"));
        assertEquals("pisică", t.translateWord("chat", "fr", "ro"));
        assertEquals("mâncați", t.translateWord("mangez", "fr", "ro"));
        assertEquals("mange", t.translateWord("mănâncă", "ro", "fr"));
    }

    @Test
    public void testTranslateWordMissingEntryThrowsException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* missing language */
        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> t.translateWord("chat", "it", "fr"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        /* missing word */
        Exception exception1 = assertThrows(ExceptionMissingEntry.class,
                () -> t.translateWord("porc", "ro", "fr"));

        String expectedMessage1 = "Missing Entry";
        String actualMessage1 = exception1.getMessage();

        assertTrue(actualMessage1.contains(expectedMessage1));


    }

    @Test
    public void testAddDefinitionForWordSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* build definition -> new edition dictionary */
        ArrayList<String> text = new ArrayList<>();
        text.add("sinonim de test");
        text.add("alt sinonim");
        Definition def = new Definition("Dicționar de sinonime",
                "synonyms", 2002, text);

        /* build definition -> new dictionary */
        ArrayList<String> text1 = new ArrayList<>();
        text1.add("definitie de test");
        text1.add("alta definitie mai lunga");
        Definition def1 = new Definition("Dicționar nou de definitii",
                "definitions", 2012, text1);

        /* build definition -> update for dictionary */
        ArrayList<String> text2 = new ArrayList<>();
        text2.add("definitie de test");
        text2.add("alta definitie mai lunga");
        text2.add("definitie de updatat");

        Definition def2 = new Definition("Dicționar nou de definitii",
                "definitions", 2012, text2);

        assertTrue(t.addDefinitionForWord("pisică", "ro", def));
        assertTrue(t.addDefinitionForWord("pisică", "ro", def1));
        assertTrue(t.addDefinitionForWord("pisică", "ro", def2));
    }

    @Test
    public void testAddDefinitionForWordFail() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);


        /* get word */
        Word word = t.getTranslator().get("ro").getWords().get(0);

        assertFalse(t.addDefinitionForWord(word.getWord(), "ro",
                word.getDefinitions().get(0)));
    }

    @Test
    public void testAddDefinitionForWordMissingEntryThrowException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* build definition -> new dictionary */
        ArrayList<String> text1 = new ArrayList<>();
        text1.add("definitie de test");
        text1.add("alta definitie mai lunga");
        Definition def = new Definition("Dicționar nou de definitii",
                "definitions", 2012, text1);

        /* missing word */
        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> t.addDefinitionForWord("porc", "ro", def));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        /* missing language */
        Exception exception2 = assertThrows(ExceptionMissingEntry.class,
                () -> t.addDefinitionForWord("pisică", "it", def));

        String expectedMessage2 = "Missing Entry";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    public void testRemoveDefinitionSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        assertTrue(t.removeDefinition("pisică", "ro",
                "Dicționar de sinonime###synonyms###1998"));
    }

    @Test
    public void testRemoveDefinitionFail() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* build and add word without definitions */
        ArrayList<String> Sg = new ArrayList<>();
        Sg.add("elefant");
        ArrayList<String> Pl = new ArrayList<>();
        Pl.add("elefanti");
        Word w2 = new Word("elefant", "elephant",
                "noun", Sg, Pl, null);

        t.addWord(w2, "ro");

        /* no definitions to remove from */
        assertFalse(t.removeDefinition("elefant", "ro",
                "Dicționar de sinonime###synonyms###1998"));
        /* nonexistent definition */
        assertFalse(t.removeDefinition("pisică", "ro",
                "Alt dictionar de sinonime###synonyms###1998"));
        /* wrong code format */
        assertFalse(t.removeDefinition("pisică", "ro",
                "Dicționar de sinonime###1998"));
        assertFalse(t.removeDefinition("pisică", "ro",
                "Dicționar de sinonimesynonyms1998"));
        assertFalse(t.removeDefinition("pisică", "ro",
                "Dicționar de sinonime_synonyms_1998"));
        assertFalse(t.removeDefinition("pisică", "ro",
                "Dicționar de sinonime#synonyms#1998"));
    }

    @Test
    public void testRemoveDefinitionForWordMissingEntryThrowException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);


        /* missing word */
        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> t.removeDefinition("porc", "ro",
                        "Dicționar de sinonime###synonyms###1998"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        /* missing language */
        Exception exception2 = assertThrows(ExceptionMissingEntry.class,
                () -> t.removeDefinition("pisică", "it",
                        "Dicționar de sinonime###synonyms###1998"));

        String expectedMessage2 = "Missing Entry";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    public void testGetDefinitionsForWordSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* get definitions and sort the lists */
        Word word = t.getTranslator().get("ro").getWords().get(0);
        ArrayList<Definition> definitions =
                new ArrayList<>(word.getDefinitions());
        Collections.sort(definitions);

        Word word1 = t.getTranslator().get("fr").getWords().get(0);
        ArrayList<Definition> definitions1 =
                new ArrayList<>(word1.getDefinitions());
        Collections.sort(definitions1);

        assertEquals(definitions,
                t.getDefinitionsForWord(word.getWord(), "ro"));
        assertEquals(definitions1,
                t.getDefinitionsForWord(word1.getWord(), "fr"));
    }

    @Test
    public void testGetDefinitionsForWordMissingEntryThrowsException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);


        /* missing word */
        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> t.getDefinitionsForWord("porc", "ro"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        /* missing language */
        Exception exception2 = assertThrows(ExceptionMissingEntry.class,
                () -> t.getDefinitionsForWord("pisică", "it"));

        String expectedMessage2 = "Missing Entry";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    public void testTranslateSentenceSuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);
        /* normal translation */
        String translation = t.translateSentence("pisică mănâncă", "ro", "fr");
        assertEquals("chat mange", translation);
        /* missing word doesn't change */
        String translation1 =
                t.translateSentence("pisică mănâncă peste", "ro", "fr");
        assertEquals("chat mange peste", translation1);
    }

    @Test
    public void testTranslateSentenceMissingEntryThrowsException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* missing language */
        Exception exception2 = assertThrows(ExceptionMissingEntry.class,
                () -> t.translateSentence("pisică mănâncă", "ro", "it"));

        String expectedMessage2 = "Missing Entry";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    public void testTranslateSentences() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* normal translation */
        ArrayList<String> translations =
                t.translateSentences("pisică mănâncă", "ro", "fr");
        ArrayList<String> translationsTest = new ArrayList<>(3);
        translationsTest.add("chat mange");
        translationsTest.add("chat absorber");
        translationsTest.add("chat becqueter");
        assertEquals(translationsTest, translations);

        /* missing word doesn't change */
        ArrayList<String> translations1 =
                t.translateSentences("pisică merge", "ro", "fr");
        ArrayList<String> translationsTest1 = new ArrayList<>(3);
        translationsTest1.add("chat merge");
        translationsTest1.add("greffier merge");
        translationsTest1.add("mistigri merge");
        assertEquals(translationsTest1, translations1);
    }

    @Test
    public void testTranslateSentencesMissingEntryThrowsException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* missing language */
        Exception exception2 = assertThrows(ExceptionMissingEntry.class,
                () -> t.translateSentences("pisică mănâncă", "ro", "it"));

        String expectedMessage2 = "Missing Entry";
        String actualMessage2 = exception2.getMessage();

        assertTrue(actualMessage2.contains(expectedMessage2));
    }

    @Test
    public void testImportTranslator() throws IOException {
        Translator t = new Translator();

        String path = "src/test/resources/import_data/ro_dict.json";
        Dictionary dic = Dictionary.importDictionary(path);
        t.addDictionary(dic, "ro");

        path = "src/test/resources/import_data/fr_dict.json";
        dic = Dictionary.importDictionary(path);
        t.addDictionary(dic, "fr");

        String pathTest = "src/test/resources/import_data";
        Translator tTest = Translator.importTranslator(pathTest);

        assertEquals(t, tTest);
    }

    @Test
    public void testImportTranslatorEmptyFolderThrowsException() {
        String path = "src/test/resources/import_data_empty";

        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> Translator.importTranslator(path));

        assertEquals("Missing Entry", exception.getMessage());
    }

    @Test
    public void testExportDictionarySuccess() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* export dictionaries */
        t.exportDictionary("ro");
        t.exportDictionary("fr");

        /* import exported dictionaries */
        Translator tExported = Translator.importTranslator("exported");
        assertEquals(t, tExported);

        /* delete created files */
        File file = new File("exported");
        File[] content = file.listFiles();
        if (content != null)
            for (File f : content) {
                f.delete();
            }
        file.delete();
    }

    @Test
    public void testExportDictionaryMissingEntryThrowsException() throws IOException {
        String path = "src/test/resources/import_data";
        Translator t = Translator.importTranslator(path);

        /* missing language */
        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> t.exportDictionary("it"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
