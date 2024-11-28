package org.senla.dao;

import org.senla.dao.quires.ReviewQueries;
import org.senla.entity.Review;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.ReviewNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao extends BaseDao{
    @Override
    public Review getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(ReviewQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToReview(resultSet);
            } else {
                throw new ReviewNotFoundException(id);
            }
        } catch(SQLException e) {
            throw new DatabaseException("Failed to get review by ID.",e);
        }
    }

    @Override
    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ReviewQueries.GET_ALL)) {

            while (resultSet.next()) {
                reviews.add(mapToReview(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get reviews.", e);
        }

        return reviews;
    }

    @Override
    public void insert(Object entity) {
        Review review = (Review) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ReviewQueries.INSERT)) {
                statement.setLong(1, review.getUser().getId());
                statement.setLong(2, review.getMovie().getId());
                statement.setLong(3, review.getRating());
                statement.setString(4, review.getComment());

                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    review.setId(resultSet.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert reviews.", e);
            }
        });
    }

    @Override
    public void update(Object entity) {
        Review review = (Review) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ReviewQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, review.getUser().getId());
                statement.setLong(2, review.getMovie().getId());
                statement.setLong(3, review.getRating());
                statement.setString(4, review.getComment());
                statement.setLong(5, review.getId());
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to update review ", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ReviewQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete member ", e);
            }
        });
    }

    private Review mapToReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getLong("id"));
        review.setRating(resultSet.getInt("rating"));
        review.setComment(resultSet.getString("comment"));

        return review;
    }
}
