package org.senla.service;

import org.senla.dao.ViewingHistoryDao;
import org.senla.entity.ViewingHistory;

import java.util.List;

public class ViewingHistoryService {
    private final ViewingHistoryDao viewingHistoryDao;

    public ViewingHistoryService(ViewingHistoryDao viewingHistoryDao) {
        this.viewingHistoryDao = viewingHistoryDao;
    }

    public ViewingHistory getById(Long id) {
        return viewingHistoryDao.getById(id);
    }

    public List<ViewingHistory> getAll() {
        return viewingHistoryDao.getAll();
    }

    public void insert(ViewingHistory viewingHistory) {
        viewingHistoryDao.insert(viewingHistory);
    }

    public void update(ViewingHistory viewingHistory) {
        viewingHistoryDao.update(viewingHistory);
    }

    public void delete(Long id) {
        viewingHistoryDao.delete(id);
    }
}
