package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public static final String ERROR_MESSAGE_USERNAME = "User with username %s not found";
    private static final String ERROR_MESSAGE_ID = "User with id %s not found";

    public UserNotFoundException(Long id) {
        super(ERROR_MESSAGE_ID.formatted(id));
    }

    public UserNotFoundException(String username) {
        super(ERROR_MESSAGE_USERNAME.formatted(username));
    }
}
