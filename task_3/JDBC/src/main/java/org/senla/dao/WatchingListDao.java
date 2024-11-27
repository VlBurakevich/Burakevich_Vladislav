package org.senla.dao;

import org.senla.dao.quires.WatchingListQueries;
import org.senla.entity.WatchingList;
import org.senla.exceptions.DatabaseException;
import org.senla.exceptions.EntityNotFoundException;
import org.senla.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WatchingListDao extends BaseDao{
    @Override
    public WatchingList getById(Long id) {
        try (Connection connection = ConnectionManager.open();
        PreparedStatement statement = connection.prepareStatement(WatchingListQueries.GET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapToWatchingList(resultSet);
            } else {
                throw new EntityNotFoundException("Movie with id " + id + " not found");
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
    public void update(Object entity, Long id) {
        WatchingList watchingList = (WatchingList) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(WatchingListQueries.INSERT)) {
                statement.setLong(1, watchingList.getUser().getId());
                statement.setLong(2, watchingList.getMovie().getId());
                statement.setTimestamp(3, watchingList.getAddedAt());
                statement.setLong(4, id);
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert movie.", e);
            }
        });
    }

    @Override
    public void delete(Long id) {
        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(WatchingListQueries.DELETE)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Failed to delete movie with ID " + id, e);
            }
        });
    }

    private WatchingList mapToWatchingList(ResultSet resultSet) throws SQLException {
        WatchingList watchingList = new WatchingList();
        watchingList.setId(resultSet.getLong("id"));
        watchingList.setAddedAt(resultSet.getTimestamp("added_at"));

        return watchingList;
    }
}
