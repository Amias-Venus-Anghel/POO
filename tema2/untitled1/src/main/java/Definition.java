import java.util.List;
import java.util.Objects;

public class Definition {
    public String dict;
    public String dictType;
    public int year;
    public List<String> text;

    public Definition(String dict, String dictType, int year, List<String> text) {
        this.dict = dict;
        this.dictType = dictType;
        this.year = year;
        this.text = text;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Definition that = (Definition) o;
        return year == that.year && dict.equals(that.dict) && dictType.equals(that.dictType) && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        int rez = Objects.hash(dict, dictType, year, text);
        System.out.println("hash code: " + rez);
        return rez;
    }
}
