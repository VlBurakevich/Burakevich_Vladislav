package com.example.exceptions;

public class ValidateException extends RuntimeException {
    public static final String ERROR_UPDATE = "Failed to validate %s";

    public ValidateException(String entityType) {
        super(ERROR_UPDATE.formatted(entityType));
    }

    public ValidateException(String entityType, Throwable cause) {
        super(ERROR_UPDATE.formatted(entityType), cause);
    }
}
