package org.senla.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class PropertyUtil {
    public static final Properties PROPERTIES = new Properties();

    static {
        loadFile();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static void loadFile() {
        try (var stream = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (stream == null) {
                throw new RuntimeException("Configuration file 'application.properties' not found");
            }
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
