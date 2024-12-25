package org.senla.dao;

import org.senla.dao.quires.ViewingHistoryQueries;
import org.senla.di.annotations.Component;
import org.senla.entity.ViewingHistory;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.entityExceptions.ViewingHistoryNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ViewingHistoryDao extends BaseDao{
    @Override
    public ViewingHistory getById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(ViewingHistoryQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToViewingHistory(resultSet);
            } else {
                throw new ViewingHistoryNotFoundException(id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get ViewingHistory by ID.", e);
        }
    }

    @Override
    public List<ViewingHistory> getAll() {
        List<ViewingHistory> viewingHistories = new ArrayList<>();
        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ViewingHistoryQueries.GET_ALL)) {

            while(resultSet.next()) {
                viewingHistories.add(mapToViewingHistory(resultSet));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to get ViewingHistory.", e);
        }

        return viewingHistories;
    }

    @Override
    public void insert(Object entity) {
        ViewingHistory viewingHistory = (ViewingHistory) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ViewingHistoryQueries.INSERT)) {
                statement.setLong(1, viewingHistory.getUser().getId());
                statement.setLong(2, viewingHistory.getMovie().getId());
                statement.setTimestamp(3, viewingHistory.getWatchedAt());
                statement.execute();

                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    viewingHistory.setId(generatedKeys.getLong(1));
                }

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert ViewingHistory.", e);
            }
        });
    }

    @Override
    public void update(Object entity) {
        ViewingHistory viewingHistory = (ViewingHistory) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ViewingHistoryQueries.UPDATE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, viewingHistory.getUser().getId());
                statement.setLong(2, viewingHistory.getMovie().getId());
                statement.setTimestamp(3, viewingHistory.getWatchedAt());
                statement.setLong(4, viewingHistory.getId());
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to update ViewingHistory.", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ViewingHistoryQueries.DELETE_PRIMARY_INFO_BY_ID)) {
                statement.setLong(1, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete ViewingHistory.", e);
            }
        });
    }

    private ViewingHistory mapToViewingHistory(ResultSet resultSet) throws SQLException {
        ViewingHistory viewingHistory = new ViewingHistory();
        viewingHistory.setId(resultSet.getLong(1));
        viewingHistory.setWatchedAt(resultSet.getTimestamp(2));

        return viewingHistory;
    }
}
