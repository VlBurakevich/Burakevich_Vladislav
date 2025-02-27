package org.senla.exceptions;

public class DatabaseSaveException extends RuntimeException {
    public static final String ERROR_SAVE_ENTITY = "Failed to save %s";

    public DatabaseSaveException(String entityType) {
        super(ERROR_SAVE_ENTITY.formatted(entityType));
    }

    public DatabaseSaveException(String entityType, Throwable cause) {
        super(ERROR_SAVE_ENTITY.formatted(entityType), cause);
    }
}
