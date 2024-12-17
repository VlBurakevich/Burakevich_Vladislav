package org.senla.service;

import org.senla.dao.WatchingListDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.WatchingList;

import java.util.List;

@Component
public class WatchingListService {

    @Autowired
    private WatchingListDao watchingListDao;

    public WatchingList getById(Long id) {
        return watchingListDao.getById(id);
    }

    public List<WatchingList> getAll() {
        return watchingListDao.getAll();
    }

    public void insert(WatchingList watchingList) {
        watchingListDao.insert(watchingList);
    }

    public void update(WatchingList watchingList) {
        watchingListDao.update(watchingList);
    }

    public void delete(Long id) {
        watchingListDao.delete(id);
    }
}
