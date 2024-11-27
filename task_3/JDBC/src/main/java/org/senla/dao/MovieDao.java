package org.senla.dao;

import org.senla.dao.quires.MovieQueries;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.EntityNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MovieDao extends BaseDao{

    @Override
    public Movie getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(MovieQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToMovie(resultSet);
            } else {
                throw new EntityNotFoundException("Movie with id " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get movie by ID.", e);
        }
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(MovieQueries.GET_ALL)) {

            while (resultSet.next()) {
                movies.add(mapToMovie(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get movies.", e);
        }

        return movies;
    }

    @Override
    public void insert(Object entity) {
        Movie movie = (Movie) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MovieQueries.INSERT)) {
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getDescription());
                statement.setTime(3, Time.valueOf(movie.getDuration().toString()));
                statement.setDate(4, Date.valueOf(movie.getReleaseDate().toLocalDate()));
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    movie.setId(resultSet.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert movie.", e);
            }
        });
    }

    @Override
    public void update(Object entity, Long id) {
        Movie movie = (Movie) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MovieQueries.UPDATE)) {
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getDescription());
                statement.setTime(3, Time.valueOf(movie.getDuration().toString()));
                statement.setDate(4, Date.valueOf(movie.getReleaseDate().toString()));
                statement.setLong(5, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to update movie with ID " + id, e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MovieQueries.DELETE)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete movie with ID " + id, e);
            }
        });
    }

    private Movie mapToMovie(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getLong(1));
        movie.setTitle(resultSet.getString(2));
        movie.setDescription(resultSet.getString(3));

        return movie;
    }
}
