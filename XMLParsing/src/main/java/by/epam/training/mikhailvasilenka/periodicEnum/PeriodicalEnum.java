package by.epam.training.mikhailvasilenka.periodicEnum;

public enum PeriodicalEnum {
    PAPERS("periodic"),
    ISSN("issn"),
    TITLE("title"),
    VOLUME("volume"),
    COLORED("colored"),
    MONTHLY("monthly"),
    GLOSSY("glossy"),
    NEWSPAPER("newspaper"),
    MAGAZINE("magazine"),
    BOOKLET("booklet");

    private String value;

    PeriodicalEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
