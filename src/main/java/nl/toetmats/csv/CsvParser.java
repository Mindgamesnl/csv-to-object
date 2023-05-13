package nl.toetmats.csv;

import nl.toetmats.csv.adapters.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CsvParser<T extends CsvModal> {

    private String csvContent;
    private Class<T> modalClass;
    private Map<Class<?>, CsvTypeAdapter<?>> adapterMap = new HashMap<>();
    private List<ValuePreprocessor> valuePreprocessors = new ArrayList<>();

    public CsvParser(String csvContent, Class<T> modalClass) {
        this.csvContent = csvContent;
        this.modalClass = modalClass;

        // register default adapters
        registerTypeAdapter(String.class, new StringAdapter());
        registerTypeAdapter(Integer.class, new IntegerAdapter());
        registerTypeAdapter(Boolean.class, new BooleanAdapter());
        registerTypeAdapter(Double.class, new DoubleAdapter());
        registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        registerTypeAdapter(Float.class, new FloatAdapter());
    }

    public void registerValuePreprocessor(ValuePreprocessor valuePreprocessor) {
        valuePreprocessors.add(valuePreprocessor);
    }

    public <T> void registerTypeAdapter(Class<T> type, CsvTypeAdapter<T> adapter) {
        adapterMap.put(type, adapter);
    }

    private String processValue(String v) {
        for (ValuePreprocessor valuePreprocessor : valuePreprocessors) {
            v = valuePreprocessor.process(v);
        }
        return v;
    }

    public Collection<T> parse() {
        List<T> list = new ArrayList<>();

        // build index of column names
        Map<String, Integer> index = new LinkedHashMap<>();
        String[] lines = csvContent.split("\n");
        String[] columns = lines[0].split(",");
        for (int i = 0; i < columns.length; i++) {
            index.put(columns[i], i);
        }

        // parse lines
        for (int i = 1; i < lines.length; i++) {
            try {
                String line = lines[i].trim();
                List<String> values = new ArrayList<>();
                boolean inQuotes = false;
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < line.length(); j++) {
                    char c = line.charAt(j);
                    if (c == ',' && !inQuotes) {
                        values.add(sb.toString().trim());
                        sb.setLength(0);
                    } else if (c == '\"') {
                        inQuotes = !inQuotes;
                    } else {
                        sb.append(c);
                    }
                }
                values.add(processValue(sb.toString().trim()));

                T modal = modalClass.newInstance();

                for (String key : index.keySet()) {
                    int columnIndex = index.get(key);
                    String value = values.get(columnIndex);
                    Object typedValue = null;

                    // is the value null or empty?
                    if (value == null || value.isEmpty()) {
                        modal.setColumn(key, null);
                        continue;
                    }

                    // find adapter
                    CsvTypeAdapter<?> adapter = adapterMap.get(modal.getColumnType(key));
                    if (adapter != null) {
                        // remove redundant spaces
                        value = processValue(value.trim());
                        try {
                            typedValue = adapter.fromString(value);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse value " + value + " for column " + key, e);
                        }
                    } else {
                        throw new RuntimeException("No adapter found for type " + modal.getColumnType(key).getSimpleName());
                    }

                    modal.setColumn(key, typedValue);
                }
                list.add(modal);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse line " + i + ": " + lines[i], e);
            }
        }

        return list;
    }


}
