package com.example.exceptions;

public class AuthenticationException extends RuntimeException {
    public static final String ERROR_AUTHENTICATION = "No authenticated user found";

    public AuthenticationException() {
        super(ERROR_AUTHENTICATION);
    }

    public AuthenticationException(Throwable cause) {
        super(ERROR_AUTHENTICATION, cause);
    }
}
