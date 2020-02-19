package by.epam.training.mikhailvasilenka.periodic;

import java.util.Objects;

public abstract class Periodical {

    private String issn;
    private String title;
    private boolean monthly;
    private boolean colored;
    private int volume;
    private boolean glossy;


    public Periodical() {
        issn = "0000-0000";
        title = "no title";
        monthly = false;
        colored = false;
        glossy = false;
        volume = 0;
    }

    public static Periodical getEmptyPeriodical() {
        return new Periodical() {};
    }

    public boolean isGlossy() {
        return glossy;
    }

    public void setGlossy(boolean glossy) {
        this.glossy = glossy;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMonthly() {
        return monthly;
    }

    public void setMonthly(boolean monthly) {
        this.monthly = monthly;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Periodical that = (Periodical) o;
        return Objects.equals(issn, that.issn);
    }

    @Override
    public int hashCode() {
        int result = 16;
        result = 31 * result + issn.hashCode() + title.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Periodical { \n" +
                "\tissn: '" + issn + "'\n" +
                "\ttitle: '" + title + "'\n" +
                "\tvolume: '" + volume + "'\n" +
                "\tmonthly: '" + monthly + "'\n" +
                "\tcolored: '" + colored + "'\n" +
                "\tglossy: '" + glossy + "' }\n\n";
    }
}
