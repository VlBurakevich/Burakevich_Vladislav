package org.senla.exceptions;


public class DatabaseException extends RuntimeException {
    public static final String ERROR_SAVE_ENTITY = "Failed to save %s";
    public static final String ERROR_DELETE_ENTITY = "Failed to delete %s";
    public static final String ERROR_GET_ENTITY = "Failed to get %s";
    public static final String ERROR_UPDATE_ENTITY = "Failed to update %s";

    public DatabaseException(String errorMassage, String entityType) {
        super(errorMassage.formatted(entityType));
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
