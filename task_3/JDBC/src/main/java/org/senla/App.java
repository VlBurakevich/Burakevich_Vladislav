package org.senla;

import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        try (Connection connection = ConnectionManager.open()) {
            System.out.println("Connected to the database");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}