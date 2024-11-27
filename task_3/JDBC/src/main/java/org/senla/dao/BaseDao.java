package org.senla.DAO;

import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseDao {
    protected Connection getConnection() throws SQLException {
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
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Object getById(Long id);
    public abstract List<?> getAll();
    public abstract void save(Object entity);
    public abstract void update(Object entity, Long id);
    public abstract void delete(Long id);
}
