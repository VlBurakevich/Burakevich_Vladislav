package org.senla.dao;

import org.senla.dao.quires.GenreQueries;
import org.senla.di.annotations.Component;
import org.senla.entity.Genre;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.GenreNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDao extends BaseDao {
    @Override
    public Genre getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(GenreQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToGenre(resultSet);
            } else {
                throw new GenreNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get genre by ID.", e);
        }
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genres = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GenreQueries.GET_ALL)) {

            while (resultSet.next()) {
                genres.add(mapToGenre(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get all genres.", e);
        }

        return genres;
    }

    @Override
    public void insert(Object entity) {
        Genre genre = (Genre) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GenreQueries.INSERT)) {
                statement.setLong(1, genre.getId());
                statement.setLong(2, genre.getParentGenre().getId());
                statement.setString(3, genre.getName());
                statement.setString(4, genre.getDescription());
                statement.execute();

                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    genre.setId(generatedKeys.getLong(1));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert genre.", e);
            }
        });
    }

    @Override
    public void update(Object entity) {
        Genre genre = (Genre) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GenreQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, genre.getParentGenre().getId());
                statement.setString(2, genre.getName());
                statement.setString(3, genre.getDescription());
                statement.setLong(4, genre.getId());
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to update ", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(GenreQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete genre ", e);
            }
        });
    }

    private Genre mapToGenre(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("id"));
        genre.setName(resultSet.getString("name"));
        genre.setDescription(resultSet.getString("description"));

        return genre;
    }

    public List<Genre> getAllByMovieId(Long movieId) {
        List<Genre> genres = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(GenreQueries.GET_ALL_BY_MOVIE_ID)) {
            preparedStatement.setLong(1, movieId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    genres.add(mapToGenres(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get genre by movie ID.", e);
        }

        return genres;
    }

    private Genre mapToGenres(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("id"));

        Long parentGenreId = resultSet.getLong("parent_genre_id");
        if (!resultSet.wasNull()) {
            Genre parentGenre = new Genre();
            parentGenre.setId(parentGenreId);
            genre.setParentGenre(parentGenre);
        }

        genre.setName(resultSet.getString("name"));
        genre.setDescription(resultSet.getString("description"));

        return genre;
    }
}
