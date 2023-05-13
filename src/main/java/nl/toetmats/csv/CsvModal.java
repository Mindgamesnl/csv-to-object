package nl.toetmats.csv;

public abstract class CsvModal {
    public Class<?> getColumnType(String key) {
        key = key.replaceAll(" ", "");
        // find field where annotation matches key
        for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(CsvField.class)) {
                CsvField annotation = field.getAnnotation(CsvField.class);
                String column = annotation.column().replaceAll(" ", "");
                if (annotation.column().equalsIgnoreCase(key)) {
                    return field.getType();
                }
            }
        }
        throw new RuntimeException("No field found for column " + key);
    }

    public void setColumn(String key, Object value) {
        key = key.replaceAll(" ", "");
        try {
            // find field where annotation matches key
            for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(CsvField.class)) {
                    CsvField annotation = field.getAnnotation(CsvField.class);
                    String column = annotation.column().replaceAll(" ", "");
                    if (annotation.column().equalsIgnoreCase(key)) {
                        // set field
                        field.setAccessible(true);
                        field.set(this, value);
                        return;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("No field found for column " + key);
    }
}
