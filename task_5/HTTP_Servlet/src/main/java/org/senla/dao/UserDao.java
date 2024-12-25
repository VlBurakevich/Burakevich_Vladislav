package org.senla.dao;

import org.senla.dao.quires.CredentialQueries;
import org.senla.dao.quires.RoleQueries;
import org.senla.dao.quires.UserQueries;
import org.senla.di.annotations.Component;
import org.senla.entity.User;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.UserNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao extends BaseDao {

    @Override
    public User getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(UserQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToUser(resultSet);
            } else {
                throw new UserNotFoundException(id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get user by ID.", e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(UserQueries.GET_ALL)) {

            while (resultSet.next()) {
                users.add(mapToUser(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get users.", e);
        }

        return users;
    }

    @Override
    public void insert(Object entity) {
        User user = (User) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(UserQueries.INSERT, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getUsername());

                statement.execute();
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert user.", e);
            }
        });
    }

    @Override
    public void update(Object entity) {
        User user = (User) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(UserQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setString(1, user.getUsername());
                statement.setLong(2, user.getId());
                statement.executeUpdate();

            } catch (Exception e) {
                throw new DatabaseException("Failed to update user.", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(UserQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete user.", e);
            }
        });
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));

        return user;
    }

    public User getByUsername(String username) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(UserQueries.GET_BY_USERNAME)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToUser(resultSet);
            } else {
                throw new UserNotFoundException(username);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get user by ID.", e);
        }
    }

    public void createUser(User user) {
        executeTransaction(connection -> {
            try {
                long userId = insertUser(connection, user);

                insertCredential(connection, userId, user);

                assignRoleToUser(connection, userId);
            } catch (SQLException e) {
                throw new DatabaseException("Failed to create user.", e);
            }
        });
    }

    private long insertUser(Connection connection, User user) throws SQLException {
        try (PreparedStatement userStatement = connection.prepareStatement(UserQueries.INSERT, Statement.RETURN_GENERATED_KEYS)) {
            userStatement.setString(1, user.getUsername());
            userStatement.executeUpdate();

            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Failed to insert new user.");
            }
        }
    }

    private void insertCredential(Connection connection, long userId, User user) throws SQLException {
        try (PreparedStatement credentialStatement = connection.prepareStatement(CredentialQueries.INSERT)) {
            credentialStatement.setLong(1, userId);
            credentialStatement.setString(2, user.getCredential().getPassword());
            credentialStatement.setString(3, user.getCredential().getEmail());
            credentialStatement.executeUpdate();
        }
    }

    private void assignRoleToUser(Connection connection, long userId) throws SQLException {
        try (PreparedStatement userRoleStatement = connection.prepareStatement(RoleQueries.INSERT_USER_ROLE)) {
            userRoleStatement.setLong(1, userId);
            userRoleStatement.setLong(2, 1);
            userRoleStatement.executeUpdate();
        }
    }
}
