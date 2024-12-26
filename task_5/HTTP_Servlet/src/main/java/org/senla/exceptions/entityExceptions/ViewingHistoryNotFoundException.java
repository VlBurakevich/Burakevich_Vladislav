package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class ViewingHistoryNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "ViewingHistory with id %s not found";

    public ViewingHistoryNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
