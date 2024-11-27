package org.senla.dao;

import org.senla.entity.ViewingHistory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ViewingHistoryDao extends BaseDao{
    @Override
    public ViewingHistory getById(Long id) {
        return null;
    }

    @Override
    public List<ViewingHistory> getAll() {
        return List.of();
    }

    @Override
    public void insert(Object entity) {

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
