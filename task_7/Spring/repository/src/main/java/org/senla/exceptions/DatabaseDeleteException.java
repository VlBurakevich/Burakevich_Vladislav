package org.senla.exceptions;

public class DatabaseDeleteException extends RuntimeException {
    public static final String ERROR_DELETE_ENTITY = "Failed to delete %s";

    public DatabaseDeleteException(String entityType) {
        super(ERROR_DELETE_ENTITY.formatted(entityType));
    }

    public DatabaseDeleteException(String entityType,Throwable cause) {
        super(ERROR_DELETE_ENTITY.formatted(entityType), cause);
    }
}
