package org.senla.service;

import org.senla.dao.WatchingListDao;
import org.senla.entity.WatchingList;

import java.util.List;

public class WatchingListService {
    private final WatchingListDao watchingListDao;

    public WatchingListService(WatchingListDao watchingListDao) {
        this.watchingListDao = watchingListDao;
    }

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
