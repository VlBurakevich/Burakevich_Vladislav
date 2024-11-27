package org.senla.dao;

import org.senla.entity.WatchingList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WatchingListDao extends BaseDao{
    @Override
    public Object getById(Long id) {
        return null;
    }

    @Override
    public List<?> getAll() {
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

    private WatchingList mapToWatchingList(ResultSet resultSet) throws SQLException {
        return new WatchingList();
    }
}
