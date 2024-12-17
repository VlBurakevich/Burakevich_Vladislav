package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class ReviewNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "Review with id %s not found";

    public ReviewNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
