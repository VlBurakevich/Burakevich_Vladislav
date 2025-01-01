package org.senla.dao;

import org.senla.dao.quires.WatchingListQueries;
import org.senla.di.annotations.Component;
import org.senla.entity.Movie;
import org.senla.entity.User;
import org.senla.entity.WatchingList;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.ViewingHistoryNotFoundException;
import org.senla.exceptions.entityExceptions.WatchingListNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class WatchingListDao extends BaseDao {
    @Override
    public WatchingList getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(WatchingListQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToWatchingList(resultSet);
            } else {
                throw new WatchingListNotFoundException(id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get movie by ID.", e);
        }
    }

    @Override
    public List<WatchingList> getAll() {
        List<WatchingList> watchingLists = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(WatchingListQueries.GET_ALL)) {

            while (resultSet.next()) {
                watchingLists.add(mapToWatchingList(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get movie list.", e);
        }

        return watchingLists;
    }

    @Override
    public void insert(Object entity) {
        WatchingList watchingList = (WatchingList) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(WatchingListQueries.INSERT)) {
                statement.setLong(1, watchingList.getUser().getId());
                statement.setLong(2, watchingList.getMovie().getId());
                statement.setTimestamp(3, watchingList.getAddedAt());
                statement.execute();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert movie.", e);
            }
        });
    }

    @Override
    public void update(Object entity) {
        WatchingList watchingList = (WatchingList) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(WatchingListQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, watchingList.getUser().getId());
                statement.setLong(2, watchingList.getMovie().getId());
                statement.setTimestamp(3, watchingList.getAddedAt());
                statement.setLong(4, watchingList.getId());
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert movie.", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(WatchingListQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete movie ", e);
            }
        });
    }

    private WatchingList mapToWatchingList(ResultSet resultSet) throws SQLException {
        WatchingList watchingList = new WatchingList();
        watchingList.setId(resultSet.getLong("id"));
        watchingList.setAddedAt(resultSet.getTimestamp("added_at"));

        return watchingList;
    }

    public void removeMovieFromList(User user, Movie movie) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(WatchingListQueries.DELETE_BY_USER_AND_MOVIE)){
            statement.setLong(1, user.getId());
            statement.setLong(2, movie.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new ViewingHistoryNotFoundException(movie.getId());
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete ViewingHistory.", e);
        }
    }

    public List<Movie> getMoviesByUser(User user) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(WatchingListQueries.GET_MOVIES_BY_USER_ID)) {
            statement.setLong(1, user.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = mapToMovie(resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get ViewingHistory.", e);
        }

        return movies;
    }

    private Movie mapToMovie(ResultSet resultSet) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getLong("id"));
        movie.setTitle(resultSet.getString("title"));
        movie.setDescription(resultSet.getString("description"));

        Time time = resultSet.getTime("duration");
        long seconds = time.toLocalTime().toSecondOfDay();
        movie.setDuration(Duration.ofSeconds(seconds));

        movie.setReleaseDate(Date.valueOf(resultSet.getDate("release_date").toLocalDate()));

        return movie;
    }
}
