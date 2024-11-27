package org.senla.dao;

import org.senla.dao.quires.CredentialQueries;
import org.senla.dao.quires.RoleQueries;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.EntityNotFoundException;
import org.senla.util.ConnectionManager;

import javax.management.relation.Role;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleDao extends BaseDao{
    @Override
    public Role getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(RoleQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToRole(resultSet);
            } else {
                throw new EntityNotFoundException("Role with id " + id + " not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get role by ID.", e);
        }
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(RoleQueries.GET_ALL)) {

            while (resultSet.next()) {
                roles.add(mapToRole(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get roles.", e);
        }

        return roles;
    }

    @Override
    public void insert(Object entity) {
        Role role = (Role) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(RoleQueries.INSERT)) {

                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    role.setId(resultSet.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert new role.", e);
            }
        });
    }

    @Override
    public void update(Object entity, Long id) {
        Role role = (Role) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(CredentialQueries.UPDATE)) {
                statement.setLong(1, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to update role with ID " + id, e);
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
                throw new DatabaseException("Failed to delete role with ID " + id, e);
            }
        })
    }

    private Role mapToRole(ResultSet resultSet) throws SQLException {
        return new Role(

        );
    }
}
