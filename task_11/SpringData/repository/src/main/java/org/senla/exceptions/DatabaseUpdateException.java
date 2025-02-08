package org.senla.exceptions;

public class DatabaseUpdateException extends RuntimeException {
    public static final String ERROR_UPDATE_ENTITY = "Failed to update %s";

    public DatabaseUpdateException(String entityType) {
        super(ERROR_UPDATE_ENTITY.formatted(entityType));
    }

    public DatabaseUpdateException(String entityType, Throwable cause) {
        super(ERROR_UPDATE_ENTITY.formatted(entityType), cause);
    }
}