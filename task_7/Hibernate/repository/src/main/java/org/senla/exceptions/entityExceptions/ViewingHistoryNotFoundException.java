package org.senla.exceptions.entityExceptions;

public class ViewingHistoryNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "ViewingHistory with id %s not found";

    public ViewingHistoryNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
