package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "Member with id %s not found";

    public MemberNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
