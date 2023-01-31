package translator.pojos;

import java.util.ArrayList;
import java.util.Objects;

public class Word implements Comparable {
    private String word;
    private String word_en;
    private String type;
    private ArrayList<String> singular;
    private ArrayList<String> plural;
    private ArrayList<Definition> definitions;

    public Word(String word, String word_en, String type, ArrayList<String> singular, ArrayList<String> plural, ArrayList<Definition> definitions) {
        this.word = word;
        this.word_en = word_en;
        this.type = type;
        this.singular = singular;
        this.plural = plural;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public String getWord_en() {
        return word_en;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getSingular() {
        return singular;
    }

    public ArrayList<String> getPlural() {
        return plural;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSingular(ArrayList<String> singular) {
        this.singular = singular;
    }

    public void setPlural(ArrayList<String> plural) {
        this.plural = plural;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word) && Objects.equals(word_en, word1.word_en) && Objects.equals(type, word1.type) && Objects.equals(singular, word1.singular) && Objects.equals(plural, word1.plural) && Objects.equals(definitions, word1.definitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, word_en, type, singular, plural, definitions);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", word_en='" + word_en + '\'' +
                ", type='" + type + '\'' +
                ", singular=" + singular +
                ", plural=" + plural +
                ", definitions=" + definitions +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.word.compareTo(((Word)o).word);
    }
}
