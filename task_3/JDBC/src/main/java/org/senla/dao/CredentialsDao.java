package org.senla.dao;

import org.senla.dao.quires.CredentialQueries;
import org.senla.entity.Credential;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.EntityNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialsDao extends BaseDao{
    @Override
    public Object getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(CredentialQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToCredentials(resultSet);
            } else {
                throw new EntityNotFoundException("Credential with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get credential by ID.", e);
        }
    }

    @Override
    public List<Credential> getAll() {
        List<Credential> credentials = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(CredentialQueries.GET_ALL)) {

            while (resultSet.next()) {
                credentials.add(mapToCredentials(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get credentials.", e);
        }

        return credentials;
    }

    @Override
    public void insert(Object entity) {
        Credential credential = (Credential) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(CredentialQueries.INSERT)) {
                statement.setLong(1, credential.getUser().getId());
                statement.setString(2, credential.getPassword());
                statement.setString(3, credential.getEmail());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    credential.setId(resultSet.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert credential.", e);
            }
        });
    }

    @Override
    public void update(Object entity, Long id) {
        Credential credential = (Credential) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(CredentialQueries.UPDATE)) {
                statement.setLong(1, credential.getUser().getId());
                statement.setString(2, credential.getPassword());
                statement.setString(3, credential.getEmail());
                statement.setLong(4, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to update credential with ID " + id, e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
           try (PreparedStatement statement = connection.prepareStatement(CredentialQueries.DELETE)) {
               statement.setLong(1, id);
               statement.executeUpdate();

           } catch (SQLException e) {
               throw new DatabaseException("Failed to delete credential with ID " + id, e);
           }
        });
    }

    private Credential mapToCredentials(ResultSet resultSet) throws SQLException {
        Credential credential = new Credential();
        credential.setId(resultSet.getLong("id"));
        credential.setPassword(resultSet.getString("password"));
        credential.setEmail(resultSet.getString("email"));

        return credential;
    }
}
