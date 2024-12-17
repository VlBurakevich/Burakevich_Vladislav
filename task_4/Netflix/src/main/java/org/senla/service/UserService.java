package org.senla.service;

import org.senla.dao.UserDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.User;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void insert(User user) {
        userDao.insert(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }
}
