package nl.toetmats.csv;

public abstract class CsvTypeAdapter<T> {

    public abstract T fromString(String value);
    public abstract String toString(T value);

}
