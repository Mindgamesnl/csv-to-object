package nl.toetmats.csv.adapters;

import nl.toetmats.csv.CsvTypeAdapter;

import java.time.LocalDateTime;

public class LocalDateTimeAdapter extends CsvTypeAdapter<LocalDateTime> {
    @Override
    public LocalDateTime fromString(String value) {
        // unescape , and "
        // parse 2022-05-04 17:19:31
        value = value.replaceAll("\\,", ",").replaceAll("\\\"", "\"");
        String[] parts = value.split("-");
        String[] timeParts = parts[2].split(" ");
        String[] time = timeParts[1].split(":");

        return LocalDateTime.of(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(timeParts[0]),
                Integer.parseInt(time[0]),
                Integer.parseInt(time[1]),
                Integer.parseInt(time[2])
        );
    }

    @Override
    public String toString(LocalDateTime d) {
        return d.getDayOfMonth() + "-" + d.getMonthValue() + "-" + d.getYear() + " " + d.getHour() + ":" + d.getMinute() + ":" + d.getSecond();
    }
}
