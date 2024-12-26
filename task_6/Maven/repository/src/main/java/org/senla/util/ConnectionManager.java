package org.senla.util;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class ConnectionManager {
    public static final String URL_KEY = "db.url";
    public static final String USERNAME_KEY = "db.username";
    public static final String PASSWORD_KEY = "db.password";

    static {
        loadDriver();
    }

    public static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgresSQL JDBC Driver not found", e);
        }

    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertyUtil.get(URL_KEY),
                    PropertyUtil.get(USERNAME_KEY),
                    PropertyUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
