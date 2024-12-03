package org.senla.di.exceptiojns;

public class FieldProcessingException extends RuntimeException {
    public FieldProcessingException(String message) {
        super(message);
    }

    public FieldProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
