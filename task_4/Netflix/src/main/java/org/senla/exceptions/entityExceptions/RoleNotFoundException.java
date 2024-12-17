package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "Role with id %s not found";

    public RoleNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
