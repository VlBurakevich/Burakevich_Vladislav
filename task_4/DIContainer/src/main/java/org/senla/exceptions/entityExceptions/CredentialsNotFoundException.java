package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class CredentialsNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "Credential with id %s not found";

    public CredentialsNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
