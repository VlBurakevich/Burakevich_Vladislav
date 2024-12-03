package org.senla.dao;

import lombok.NoArgsConstructor;
import org.senla.di.annotations.Component;
import org.senla.exceptions.DatabaseException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseDao {
    protected Connection getConnection() {
        return ConnectionManager.open();
    }

    protected void executeTransaction(Consumer<Connection> action) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try {
                action.accept(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseException("Transaction failed. Rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to obtain a connection", e);
        }
    }

    public abstract Object getById(Long id);
    public abstract List<?> getAll();
    public abstract void insert(Object entity);
    public abstract void update(Object entity);
    public abstract void delete(Long id);
}
