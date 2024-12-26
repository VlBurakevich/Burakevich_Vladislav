package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class WatchingListNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "WatchingList with id %s not found";

    public WatchingListNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
