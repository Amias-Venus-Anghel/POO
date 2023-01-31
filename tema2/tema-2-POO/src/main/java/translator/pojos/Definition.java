package translator.pojos;

import java.util.ArrayList;
import java.util.Objects;

public class Definition implements Comparable {
    private String dict;
    private String dictType;
    private int year;
    private ArrayList<String> text;

    public Definition(String dict, String dictType, int year, ArrayList<String> text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public String getDict() {
        return dict;
    }

    public String getDictType() {
        return dictType;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return year == that.year && Objects.equals(dict, that.dict) && Objects.equals(dictType, that.dictType) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dict, dictType, year, text);
    }

    @Override
    public String toString() {
        return "Definition{" +
                "dict='" + dict + '\'' +
                ", dictType='" + dictType + '\'' +
                ", year=" + year +
                ", text=" + text +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return this.year - ((Definition)o).year;
    }
}
