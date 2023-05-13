package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

public class StringAdapter extends CsvTypeAdapter<String> {
    @Override
    public String fromString(String value) {
        // unescape , and "
        return value.replaceAll("\\,", ",").replaceAll("\\\"", "\"");
    }

    @Override
    public String toString(String value) {
        // escape , and "
        return value.replaceAll(",", "\\,").replaceAll("\"", "\\\"");
    }
}
