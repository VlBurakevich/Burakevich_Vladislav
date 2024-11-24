package org.senla.DAO;

import org.senla.entity.Movie;
import org.senla.util.ConnectionManager;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MovieDao extends BaseDao{

    @Override
    public Movie getById(Long id) {
        String sql = """
                SELECT id, title, description, duration, release_date
                FROM movies
                WHERE id = ?;
                """;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToMovie(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Movie> getAll() {
        String sql = """
                SELECT id, title, description, duration, release_date
                FROM movies;
                """;
        List<Movie> movies = new ArrayList<>();

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
        Movie movie = (Movie) entity;
        String sql = """
                INSERT INTO movies (title, description, duration, release_date)
                VALUES (?, ?, ?, ?)
                """;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getDescription());
                statement.setTime(3, Time.valueOf(movie.getDuration().toString()));
                statement.setDate(4, Date.valueOf(movie.getReleaseDate()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void update(Object entity, Long id) {
        Movie movie = (Movie) entity;
        String sql = """
                UPDATE movies
                SET title = ?, description = ?, duration = ?, release_date = ?
                WHERE id = ?
                """;
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getDescription());
                statement.setTime(3, Time.valueOf(movie.getDuration().toString()));
                statement.setDate(4, Date.valueOf(movie.getReleaseDate().toString()));
                statement.setLong(5, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        String sql = """
                DELETE FROM movies
                WHERE id = ?;
                """;
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Movie mapToMovie(ResultSet resultSet) throws SQLException {
        return new Movie(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                Duration.ofSeconds(resultSet.getLong("duration")),
                resultSet.getDate("release_date").toLocalDate()
        );
    }
}
