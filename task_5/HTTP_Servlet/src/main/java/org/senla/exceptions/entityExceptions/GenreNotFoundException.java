package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class GenreNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "Genre with id %s not found";

    public GenreNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }
}
