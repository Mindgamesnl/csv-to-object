package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

public class IntegerAdapter extends CsvTypeAdapter<Integer> {
    @Override
    public Integer fromString(String value) {
        // unescape , and "
        String replaced = value.replaceAll("\\,", ",").replaceAll("\\\"", "\"");

        // drop decimals
        replaced = replaced.split("\\.")[0];
        replaced = replaced.replaceAll("\"", "");
        return Integer.parseInt(replaced);
    }

    @Override
    public String toString(Integer d) {
        return d.toString();
    }
}
