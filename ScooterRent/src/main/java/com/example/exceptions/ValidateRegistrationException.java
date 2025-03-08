package com.example.exceptions;

public class ValidateRegistrationException extends RuntimeException {
    public static final String ERROR_UPDATE = "Failed to validate %s";

    public ValidateRegistrationException(String entityType) {
        super(ERROR_UPDATE.formatted(entityType));
    }

    public ValidateRegistrationException(String entityType, Throwable cause) {
        super(ERROR_UPDATE.formatted(entityType), cause);
    }
}
