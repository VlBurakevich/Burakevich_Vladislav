package org.senla.exceptions;

public class DatabaseGetException extends RuntimeException {
    public static final String ERROR_GET_ENTITY = "Failed to get %s";

    public DatabaseGetException(String entityType) {
    super(ERROR_GET_ENTITY.formatted(entityType));
}

    public DatabaseGetException(String entityType, Throwable cause) {
        super(ERROR_GET_ENTITY.formatted(entityType), cause);
    }
}
