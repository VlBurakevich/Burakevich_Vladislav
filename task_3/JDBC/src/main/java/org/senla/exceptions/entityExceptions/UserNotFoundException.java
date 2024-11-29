package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "User with id %s not found";

    public UserNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
