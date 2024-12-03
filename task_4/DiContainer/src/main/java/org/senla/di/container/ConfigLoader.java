package org.senla.di.container;

import org.senla.di.exceptiojns.ConfigurationException;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {
    private final Map<String, String> configMap = new HashMap<>();

    public ConfigLoader(String fileName) {
        try {
            load(fileName);
        } catch (ConfigurationException e) {
            throw new ConfigurationException(ConfigurationException.CONFIG_FILE_NOT_FOUND, fileName);
        }
    }

    private void load(String fileName) {
        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new ConfigurationException("Failed to load configuration file: ");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new ConfigurationException(ConfigurationException.CONFIG_LOADING_FAILED, fileName, e);
        }

        for (String key : properties.stringPropertyNames()) {
            configMap.put(key, properties.getProperty(key));
        }
    }

    public Map<String, String> getConfig() {
        return configMap;
    }
}
