package org.senla.exceptions.entityExceptions;

public class ReviewNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Review with id %s not found";

    public ReviewNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
