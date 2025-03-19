package com.example.exceptions;

public class DeleteException extends RuntimeException {
    public static final String ERROR_DELETE = "Failed to delete %s";

    public DeleteException(String entityType) {
        super(ERROR_DELETE.formatted(entityType));
    }

    public DeleteException(String entityType, Throwable cause) {
        super(ERROR_DELETE.formatted(entityType), cause);
    }
}
