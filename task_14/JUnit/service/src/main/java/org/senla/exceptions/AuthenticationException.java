package org.senla.exceptions;

public class AuthenticationException extends RuntimeException {
    public static final String AUTHENTICATION_ERROR = "Authentication error";

    public AuthenticationException() {
        super(AUTHENTICATION_ERROR);
    }
}
