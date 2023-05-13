package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

public class FloatAdapter extends CsvTypeAdapter<Float> {
    @Override
    public Float fromString(String value) {
        // parse values like "9,199.00"
        String replaced = value.replaceAll("\\,", "").replaceAll("\"", "");
        return Float.parseFloat(replaced);
    }

    @Override
    public String toString(Float d) {
        String value = d.toString();
        String replaced = value.replaceAll(",", "\\,").replaceAll("\"", "\\\"");
        return replaced;
    }
}
