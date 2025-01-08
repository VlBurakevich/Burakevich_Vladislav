package org.senla.exceptions.entityExceptions;

public class RoleNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Role with id %s not found";

    public RoleNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
