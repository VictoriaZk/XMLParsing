package by.epam.training.mikhailvasilenka.periodic;

public class Magazine extends Periodical {

    private boolean glossy;

    public boolean isGlossy() {
        return glossy;
    }

    public void setGlossy(boolean glossy) {
        this.glossy = glossy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Magazine magazine = (Magazine) o;
        return glossy == magazine.glossy;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (glossy ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Magazine : " + super.toString();
    }
}
