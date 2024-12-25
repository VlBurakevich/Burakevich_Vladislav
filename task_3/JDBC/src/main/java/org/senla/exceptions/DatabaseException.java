package org.senla.exceptions;

import java.sql.SQLException;

public class DatabaseException  extends RuntimeException {
    public DatabaseException(SQLException message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
