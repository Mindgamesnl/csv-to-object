package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

import java.time.LocalDate;

public class LocalDateAdapter extends CsvTypeAdapter<LocalDate> {
    @Override
    public LocalDate fromString(String value) {
        // unescape , and "
        // parse like 18-12-2018
        value = value.replaceAll("\\,", ",").replaceAll("\\\"", "\"");
        String[] parts = value.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return LocalDate.of(year, month, day);
    }

    @Override
    public String toString(LocalDate d) {
        return d.getDayOfMonth() + "-" + d.getMonthValue() + "-" + d.getYear();
    }
}
