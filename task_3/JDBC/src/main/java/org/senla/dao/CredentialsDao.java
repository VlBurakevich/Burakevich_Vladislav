package org.senla.DAO;

import org.senla.entity.Credentials;
import org.senla.entity.Movie;
import org.senla.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialsDao extends BaseDao{

    @Override
    public Object getById(Long id) {
        String sql = """
                SELECT id, user_id, password, email
                FROM credentials
                WHERE id = ?;
                """;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToCredentials(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<?> getAll() {
        String sql = """
                SELECT id, user_id, password, email
                FROM credentials;
                """;
        List<Credentials> credentials = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                movies.add(mapToMovie(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    @Override
    public void save(Object entity) {

    }

    @Override
    public void update(Object entity, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    private Object mapToCredentials(ResultSet resultSet) {
        return new Credentials(

        );
    }
}
