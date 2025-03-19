package com.example.exceptions;

public class UpdateException extends RuntimeException {
    public static final String ERROR_UPDATE = "Failed to update %s";

    public UpdateException(String entityType) {
        super(ERROR_UPDATE.formatted(entityType));
    }

    public UpdateException(String entityType, Throwable cause) {
        super(ERROR_UPDATE.formatted(entityType), cause);
    }
}
