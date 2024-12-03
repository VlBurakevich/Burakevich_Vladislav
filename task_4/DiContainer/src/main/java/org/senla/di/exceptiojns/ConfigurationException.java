package org.senla.di.exceptiojns;

public class ConfigurationException extends RuntimeException {
    public static final String CONFIG_FILE_NOT_FOUND = "Config file not found: %s";
    public static final String CONFIG_LOADING_FAILED = "Failed to load configuration file: %s";

    public ConfigurationException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ConfigurationException(String message, Throwable cause, Object... args) {
        super(String.format(message, args), cause);
    }
}
