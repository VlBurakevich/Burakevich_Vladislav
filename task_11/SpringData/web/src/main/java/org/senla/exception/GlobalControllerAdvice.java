package org.senla.exception;

import org.senla.exceptions.DatabaseDeleteException;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.DatabaseGetException;
import org.senla.exceptions.DatabaseSaveException;
import org.senla.exceptions.DatabaseUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({
            DatabaseException.class,
            DatabaseDeleteException.class,
            DatabaseGetException.class,
            DatabaseSaveException.class,
            DatabaseUpdateException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleDatabaseException(DatabaseException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        return buildErrorResponse(ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(errorResponseDto);
    }
}
