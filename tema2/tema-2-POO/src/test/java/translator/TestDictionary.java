package translator;

import org.junit.jupiter.api.Test;
import translator.pojos.MessengerWord;
import translator.pojos.Word;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestDictionary {

    @Test
    public void testAddEntryNew() {
        Dictionary dic = new Dictionary();

        /* entry build */
        ArrayList<String> catelSg = new ArrayList<>();
        catelSg.add("catel");
        ArrayList<String> catelPl = new ArrayList<>();
        catelPl.add("catei");
        Word w1 = new Word("catel", "dog", "noun",
                catelSg, catelPl, null);
        assertTrue(dic.addEntry(w1));
    }

    @Test
    public void testAddEntryExistent() {
        Dictionary dic = new Dictionary();

        /* entry build */
        ArrayList<String> catelSg = new ArrayList<>();
        catelSg.add("catel");
        ArrayList<String> catelPl = new ArrayList<>();
        catelPl.add("catei");
        Word w1 = new Word("catel", "dog", "noun",
                catelSg, catelPl, null);
        dic.addEntry(w1);
        assertFalse(dic.addEntry(w1));
    }


    @Test
    public void testTranslateLgToEnNounNoEntryThrowsException() {
        Dictionary dic = new Dictionary();

        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> dic.translateLgToEn("pisica"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTranslateLgToEnVerbNoEntryThrowsException() {
        Dictionary dic = new Dictionary();

        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> dic.translateLgToEn("cade"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTranslateLgToEnNounFoundEntry() {
        Dictionary dic = new Dictionary();

        /* entry build */
        ArrayList<String> catelSg = new ArrayList<>();
        catelSg.add("catel");
        ArrayList<String> catelPl = new ArrayList<>();
        catelPl.add("catei");
        Word w1 = new Word("catel", "dog", "noun",
                catelSg, catelPl, null);
        dic.addEntry(w1);

        /* messager word build */
        MessengerWord mw = new MessengerWord(w1.getWord_en(), w1.getType(), true, 0);
        assertEquals(mw, dic.translateLgToEn("catel"));
    }

    @Test
    public void testTranslateLgToEnVerbFoundEntry() {
        Dictionary dic = new Dictionary();

        /* entry build */
        ArrayList<String> mergeSg = new ArrayList<>();
        mergeSg.add("merg");
        mergeSg.add("mergi");
        mergeSg.add("merge");
        ArrayList<String> mergePl = new ArrayList<>();
        mergePl.add("mergem");
        mergePl.add("mergeti");
        mergePl.add("merg");
        Word w1 = new Word("merge", "walk", "verb",
                mergeSg, mergePl, null);
        dic.addEntry(w1);

        /* messager word build */
        MessengerWord mw = new MessengerWord(w1.getWord_en(), w1.getType(), true, 1);

        assertEquals(mw, dic.translateLgToEn("mergi"));
    }

    @Test
    public void testTranslateEnToLgNoEntryThrowsException() {
        Dictionary dic = new Dictionary();
        MessengerWord mw = new MessengerWord("cat", "noun", true, 0);
        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> dic.translateEnToLg(mw));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTranslateEnToLgNounFoundEntry() {
        Dictionary dic = new Dictionary();
        /* entry build */
        ArrayList<String> catelSg = new ArrayList<>();
        catelSg.add("catel");
        ArrayList<String> catelPl = new ArrayList<>();
        catelPl.add("catei");
        Word w1 = new Word("catel", "dog", "noun",
                catelSg, catelPl, null);
        dic.addEntry(w1);

        /* messager word build */
        MessengerWord mw = new MessengerWord("dog", "noun", true, 0);

        assertEquals("catel", dic.translateEnToLg(mw));
    }

    @Test
    public void testTranslateEnToLgVerbFoundEntry() {
        Dictionary dic = new Dictionary();
        /* entry build */
        ArrayList<String> mergeSg = new ArrayList<>();
        mergeSg.add("merg");
        mergeSg.add("mergi");
        mergeSg.add("merge");
        ArrayList<String> mergePl = new ArrayList<>();
        mergePl.add("mergem");
        mergePl.add("mergeti");
        mergePl.add("merg");
        Word w1 = new Word("merge", "walk", "verb",
                mergeSg, mergePl, null);
        dic.addEntry(w1);

        /* messager word build */
        MessengerWord mw = new MessengerWord(w1.getWord_en(), w1.getType(), true, 1);

        assertEquals("mergi", dic.translateEnToLg(mw));
    }

    @Test
    public void testRemoveEntryFailedThrowsException() {
        Dictionary dic = new Dictionary();
        /* entry build */
        ArrayList<String> mergeSg = new ArrayList<>();
        mergeSg.add("merg");
        mergeSg.add("mergi");
        mergeSg.add("merge");
        ArrayList<String> mergePl = new ArrayList<>();
        mergePl.add("mergem");
        mergePl.add("mergeti");
        mergePl.add("merg");
        Word w1 = new Word("merge", "walk", "verb",
                mergeSg, mergePl, null);
        dic.addEntry(w1);

        Exception exception = assertThrows(ExceptionMissingEntry.class,
                () -> dic.removeEntry("caine"));

        String expectedMessage = "Missing Entry";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRemoveEntrySuccessful() {
        Dictionary dic = new Dictionary();
        /* entry build */
        ArrayList<String> mergeSg = new ArrayList<>();
        mergeSg.add("merg");
        mergeSg.add("mergi");
        mergeSg.add("merge");
        ArrayList<String> mergePl = new ArrayList<>();
        mergePl.add("mergem");
        mergePl.add("mergeti");
        mergePl.add("merg");
        Word w1 = new Word("merge", "walk", "verb",
                mergeSg, mergePl, null);
        dic.addEntry(w1);

        /* entry build */
        ArrayList<String> catelSg = new ArrayList<>();
        catelSg.add("catel");
        ArrayList<String> catelPl = new ArrayList<>();
        catelPl.add("catei");
        Word w2 = new Word("catel", "dog", "noun",
                catelSg, catelPl, null);
        dic.addEntry(w2);

        assertDoesNotThrow(() -> dic.removeEntry("catel"));
    }

    @Test
    public void testImportDictionary() throws IOException {

        String path = "src/test/resources/dictionaryRoEn_2.json";
        Dictionary dic1 = Dictionary.importDictionary(path);

        Dictionary dic2 = new Dictionary();

        /* entry build */
        ArrayList<String> catelSg = new ArrayList<>();
        catelSg.add("caine");
        ArrayList<String> catelPl = new ArrayList<>();
        catelPl.add("caini");
        Word w2 = new Word("caine", "dog", "noun",
                catelSg, catelPl, null);
        dic2.addEntry(w2);

        /* entry build */
        ArrayList<String> mergeSg = new ArrayList<>();
        mergeSg.add("merg");
        mergeSg.add("mergi");
        mergeSg.add("merge");
        ArrayList<String> mergePl = new ArrayList<>();
        mergePl.add("mergem");
        mergePl.add("mergeti");
        mergePl.add("merg");
        Word w1 = new Word("merge", "walk", "verb",
                mergeSg, mergePl, null);
        dic2.addEntry(w1);

        assertEquals(dic2, dic1);
    }

    @Test
    public void testExportDictionary() throws IOException {
        String path = "src/test/resources/dictionaryRoEn_2.json";
        Dictionary dic1 = Dictionary.importDictionary(path);
        Dictionary.exportDictionary(dic1, "ro");


        Dictionary dic2 =
                Dictionary.importDictionary("exported/ro_expDict.json");
        assertEquals(dic1, dic2);
        File file = new File("exported");
        File[] content = file.listFiles();
        if (content != null)
            for (File f : content) {
                f.delete();
            }
        file.delete();

    }

}
