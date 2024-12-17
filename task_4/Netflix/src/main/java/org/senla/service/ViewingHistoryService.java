package org.senla.service;

import org.senla.dao.ViewingHistoryDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.ViewingHistory;

import java.util.List;

@Component
public class ViewingHistoryService {

    @Autowired
    private ViewingHistoryDao viewingHistoryDao;

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
