package com.example.exceptions;

public class GetException extends RuntimeException {
    public static final String ERROR_GET = "Failed to get %s";

    public GetException(String entityType) {
        super(ERROR_GET.formatted(entityType));
    }

    public GetException(String entityType, Throwable cause) {
        super(ERROR_GET.formatted(entityType), cause);
    }
}
