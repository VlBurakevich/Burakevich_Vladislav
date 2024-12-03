package org.senla.di.container;

import org.senla.di.annotations.Value;
import org.senla.di.exceptiojns.ConfigurationException;
import org.senla.di.exceptiojns.FieldProcessingException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

public class ConfigProcessor {
    private final Map<String, String> config;

    public ConfigProcessor(Map<String, String> config) {
        this.config = config;
    }

    public void process(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Value.class)) {
                processField(obj, field);
            }
        }
    }

    private void processField(Object obj, Field field) {
        Value valueAnnotation = field.getAnnotation(Value.class);
        String key = valueAnnotation.key();
        String configValue = config.get(key);

        if (configValue != null) {
            injectConfigValue(obj, field, configValue);
        } else {
            System.out.printf("Config value not found for key: %s%n", key);
        }
    }

    private void injectConfigValue(Object obj, Field field, String configValue) {
        Object value = convertValue(field.getType(), configValue);
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException("Failed to inject config value for field: " + field.getName(), e);
        } finally {
            field.setAccessible(false);
        }
    }

    private Object convertValue(Class<?> type, String value) {
        try {
            return switch (type.getName()) {
                case "java.lang.String" -> value;
                case "int", "java.lang.Integer" -> Integer.parseInt(value);
                case "double", "java.lang.Double" -> Double.parseDouble(value);
                case "java.math.BigDecimal" -> new BigDecimal(value);
                default -> throw new IllegalArgumentException("Unsupported type: " + type);
            };
        } catch (Exception e) {
            throw new FieldProcessingException("Error converting value", e);
        }
    }
}
