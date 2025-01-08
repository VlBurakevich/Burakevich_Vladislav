package org.senla.exceptions.entityExceptions;

public class GenreNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Genre with id %s not found";

    public GenreNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
