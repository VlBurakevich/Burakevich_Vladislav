package org.senla.exceptions;


public class DatabaseException extends RuntimeException {
    public DatabaseException(String errorMassage, String entityType) {
        super(errorMassage.formatted(entityType));
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
