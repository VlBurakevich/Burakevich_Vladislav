package org.senla.exceptions.entityExceptions;

public class CredentialsNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Credential with id %s not found";

    public CredentialsNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }

    public CredentialsNotFoundException(String email) {
        super(ERROR_MESSAGE.formatted(email));
    }
}
