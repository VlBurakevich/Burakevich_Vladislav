package org.senla.exceptions.entityExceptions;

import org.senla.exceptions.EntityNotFoundException;

public class MovieNotFoundException extends EntityNotFoundException {
    private static final String ERROR_MESSAGE = "Movie with id %s not found";

    public MovieNotFoundException(Long id) {
        super(ERROR_MESSAGE.formatted(id));
    }

}
