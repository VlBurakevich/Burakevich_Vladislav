package org.senla.exceptions.entityExceptions;

public class MovieNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Movie with id %s not found";

    public MovieNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }

}
