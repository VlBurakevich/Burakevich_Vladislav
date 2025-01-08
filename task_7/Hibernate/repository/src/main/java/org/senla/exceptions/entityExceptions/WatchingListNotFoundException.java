package org.senla.exceptions.entityExceptions;

public class WatchingListNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "WatchingList with id %s not found";

    public WatchingListNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }

    public WatchingListNotFoundException(String parameter) {
        super(ERROR_MESSAGE.formatted(parameter));
    }
}
