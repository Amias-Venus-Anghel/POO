package translator.pojos;

import java.util.Objects;

public class MessengerWord {
    private String en;
    private String type;
    private boolean singular;
    private int verbPers;


    public MessengerWord(){}

    public MessengerWord(String en, String type, boolean singular, int verbPers) {
        this.en = en;
        this.type = type;
        this.singular = singular;
        this.verbPers = verbPers;
    }


    public String getEn() {
        return en;
    }

    public String getType() {
        return type;
    }

    /**
     * returns if word is singular or plural
     * @return true if single
     *          false if plural
     */
    public boolean isSingular() {
        return singular;
    }

    public int getVerbPers() {
        return verbPers;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSingular(boolean singular) {
        this.singular = singular;
    }

    public void setVerbPers(int verbPers) {
        this.verbPers = verbPers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessengerWord that = (MessengerWord) o;
        return singular == that.singular && verbPers == that.verbPers &&
                Objects.equals(en, that.en) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(en, type, singular, verbPers);
    }

    @Override
    public String toString() {
        return "MessengerWord{" +
                "en='" + en + '\'' +
                ", type='" + type + '\'' +
                ", singular=" + singular +
                ", verbPers=" + verbPers +
                '}';
    }
}
