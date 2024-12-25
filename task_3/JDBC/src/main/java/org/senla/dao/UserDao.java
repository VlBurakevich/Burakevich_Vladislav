package org.senla.dao;

import org.senla.dao.quires.UserQueries;
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

public class UserDao extends BaseDao{
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

       } catch (SQLException e ) {
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
}
