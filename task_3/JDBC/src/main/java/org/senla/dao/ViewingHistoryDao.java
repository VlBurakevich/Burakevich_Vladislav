package org.senla.dao;

import org.senla.dao.quires.CredentialQueries;
import org.senla.dao.quires.ViewingHistoryQueries;
import org.senla.entity.ViewingHistory;
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

public class ViewingHistoryDao extends BaseDao{
    @Override
    public ViewingHistory getById(Long id) {
        throw new UnsupportedOperationException("Use getByCompositeId for ViewingHistory entities.");
    }

    public ViewingHistory getByCompositeId(ViewingHistory compositeId) {}

    @Override
    public List<ViewingHistory> getAll() {
        List<ViewingHistory> viewingHistories = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(ViewingHistoryQueries.GET_ALL)) {

            while (resultSet.next()) {
                viewingHistories.add(mapToViewingHistory(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to get viewing history", e);
        }

        return viewingHistories;
    }

    @Override
    public void insert(Object entity) {
        ViewingHistory viewingHistory = (ViewingHistory) entity;

        executeTransaction(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(ViewingHistoryQueries.INSERT)) {
                statement.setLong(1, viewingHistory.getId());
                statement.execute();
                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    viewingHistory.setId(resultSet.getLong(1));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Failed to insert viewing history", e);
            }
        });
    }

    @Override
    public void update(Object entity, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    private ViewingHistory mapToViewingHistory(ResultSet resultSet) throws SQLException {
        return new ViewingHistory();

    }
}
