package org.senla.exceptions.entityExceptions;

public class MemberNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Member with id %s not found";

    public MemberNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
