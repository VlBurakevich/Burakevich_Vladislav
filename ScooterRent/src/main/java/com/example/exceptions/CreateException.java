package com.example.exceptions;

public class CreateException extends RuntimeException {
    public static final String ERROR_SAVE = "Failed to save %s";

    public CreateException(String entityType) {
        super(ERROR_SAVE.formatted(entityType));
    }

    public CreateException(String entityType, Throwable cause) {
        super(ERROR_SAVE.formatted(entityType), cause);
    }
}
