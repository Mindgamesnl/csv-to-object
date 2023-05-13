package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

public class BooleanAdapter extends CsvTypeAdapter<Boolean> {
    @Override
    public Boolean fromString(String value) {
        // unescape , and "
        value = value.replaceAll("\\,", ",").replaceAll("\\\"", "\"");

        if (value.equalsIgnoreCase("ja") || value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1")) {
            return true;
        } else if (value.equalsIgnoreCase("nee") || value.equalsIgnoreCase("no") || value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0")) {
            return false;
        } else {
            throw new IllegalArgumentException("Value is not a boolean: " + value);
        }
    }

    @Override
    public String toString(Boolean d) {
        return d ? "Ja" : "Nee";
    }
}
