package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

public class DoubleAdapter extends CsvTypeAdapter<Double> {
    @Override
    public Double fromString(String value) {
        // unescape , and "
        String replaced = value.replaceAll("\\,", ",").replaceAll("\\\"", "\"");
        replaced = replaced.replaceAll("\"", "");
        return Double.parseDouble(replaced);
    }

    @Override
    public String toString(Double d) {
        String value = d.toString();
        String replaced = value.replaceAll(",", "\\,").replaceAll("\"", "\\\"");
        return replaced;
    }
}
