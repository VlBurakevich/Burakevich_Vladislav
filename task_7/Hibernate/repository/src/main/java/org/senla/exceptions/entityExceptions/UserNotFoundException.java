package org.senla.exceptions.entityExceptions;

public class UserNotFoundException extends RuntimeException {
    public static final String ERROR_MESSAGE = "User with id %s not found";

    public static final String ERROR_MESSAGE_USER = "User with usaername %s not found";

    public UserNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
    public UserNotFoundException(String username) {
        super(ERROR_MESSAGE_USER.formatted(username));
    }
}
