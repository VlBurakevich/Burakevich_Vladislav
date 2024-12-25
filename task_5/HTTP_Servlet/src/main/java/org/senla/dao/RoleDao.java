package org.senla.dao;

import org.senla.dao.quires.RoleQueries;
import org.senla.di.annotations.Component;
import org.senla.entity.Role;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.RoleNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleDao extends BaseDao {
    @Override
    public Role getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(RoleQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToRole(resultSet);
            } else {
                throw new RoleNotFoundException(id);
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
                statement.setString(1, role.getName());
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
    public void update(Object entity) {
        Role role = (Role) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(RoleQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setString(1, role.getName());
                statement.setLong(2, role.getId());
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to update role ", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(RoleQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete role", e);
            }
        });
    }

    private Role mapToRole(ResultSet resultSet) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong("id"));
        role.setName(resultSet.getString("name"));

        return role;
    }
}
