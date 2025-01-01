package org.senla.dao;

import org.senla.dao.quires.GenreQueries;
import org.senla.dao.quires.MemberQueries;
import org.senla.dao.quires.MovieQueries;
import org.senla.di.annotations.Component;
import org.senla.entity.Genre;
import org.senla.entity.Member;
import org.senla.entity.Movie;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.MovieNotFoundException;
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
public class MovieDao extends BaseDao {

    @Override
    public Movie getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(MovieQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToMovie(resultSet);
            } else {
                throw new MovieNotFoundException(id);
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
    public void update(Object entity) {
        Movie movie = (Movie) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MovieQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setString(1, movie.getTitle());
                statement.setString(2, movie.getDescription());
                statement.setTime(3, Time.valueOf(movie.getDuration().toString()));
                statement.setDate(4, Date.valueOf(movie.getReleaseDate().toString()));
                statement.setLong(5, movie.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to update movie ", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(MovieQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete movie ", e);
            }
        });
    }

    public List<Movie> getRandomMovies(int count) {
        List<Movie> randomMovies = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(MovieQueries.GET_RANDOM_N_MOVIES)) {
            statement.setInt(1, count);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    randomMovies.add(mapToMovie(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get random movies.", e);
        }

        return randomMovies;
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

    public void addNewMovie(Movie movie) {
        executeTransaction(connection -> {
            try {
                long movieId = insertMovie(connection, movie);

                for (Genre genre : movie.getGenres()) {
                    long genreId = upsertGenre(connection, genre);
                    linkMovieWithGenre(connection, movieId, genreId);
                }

                for (Member member : movie.getMembers()) {
                    long genreId = insertMember(connection, member);
                    linkMovieWithGenre(connection, movieId, genreId);
                }

                for (Member member : movie.getMembers()) {
                    long memberId = insertMember(connection, member);
                    linkMovieWithMember(connection, movieId, memberId);
                }
            } catch (SQLException e) {
                throw new DatabaseException("Failed to add new movie ", e);
            }
        });
    }

    private long insertMovie(Connection connection, Movie movie) throws SQLException {
        try (PreparedStatement movieStmt = connection.prepareStatement(MovieQueries.INSERT, Statement.RETURN_GENERATED_KEYS)) {
            movieStmt.setString(1, movie.getTitle());
            movieStmt.setString(2, movie.getDescription());
            Duration duration = movie.getDuration();
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            movieStmt.setTime(3, Time.valueOf(timeString));
            movieStmt.setDate(4, Date.valueOf(movie.getReleaseDate().toLocalDate()));
            movieStmt.executeUpdate();

            try (ResultSet rs = movieStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("Failed to retrieve movie ID.");
                }
            }
        }
    }

    private long upsertGenre(Connection connection, Genre genre) throws SQLException {
        try (PreparedStatement genreStmt = connection.prepareStatement(GenreQueries.INSERT_WITHOUT_PARENT_ID,Statement.RETURN_GENERATED_KEYS)) {
            genreStmt.setString(1, genre.getName());
            genreStmt.setString(2, genre.getDescription());
            genreStmt.executeUpdate();

            try (ResultSet rs = genreStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        }

        try (PreparedStatement selectStmt = connection.prepareStatement(GenreQueries.GET_ID_BY_NAME)) {
            selectStmt.setString(1, genre.getName());
            try (ResultSet selectRs = selectStmt.executeQuery()) {
                if (selectRs.next()) {
                    return selectRs.getLong("id");
                } else {
                    throw new SQLException("Failed to retrieve genre ID.");
                }
            }
        }
    }

    private void linkMovieWithGenre(Connection connection, long movieId, long genreId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(GenreQueries.INSERT_MOVIE_GENRE_LINK)) {
            stmt.setLong(1, movieId);
            stmt.setLong(2, genreId);
            stmt.executeUpdate();
        }
    }

    private long insertMember(Connection connection, Member member) throws SQLException {
        try (PreparedStatement memberStmt = connection.prepareStatement(MemberQueries.INSERT, Statement.RETURN_GENERATED_KEYS)) {
            memberStmt.setString(1, member.getFirstName());
            memberStmt.setString(2, member.getLastName());
            memberStmt.setString(3, member.getNationality());
            memberStmt.setString(4, member.getType().name().toUpperCase());
            memberStmt.setString(5, member.getGender().name().toUpperCase());
            memberStmt.executeUpdate();

            try (ResultSet rs = memberStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new SQLException("Failed to retrieve member ID.");
                }
            }
        }
    }

    private void linkMovieWithMember(Connection connection, long movieId, long memberId) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(MemberQueries.INSERT_MOVIE_MEMBER_LINK)) {
            stmt.setLong(1, movieId);
            stmt.setLong(2, memberId);
            stmt.executeUpdate();
        }
    }
}
