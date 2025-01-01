package org.senla.service;

import org.senla.dao.RoleDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.entity.Role;

import java.util.List;

@Component
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role getById(Long id) {
        return roleDao.getById(id);
    }

    public List<Role> getAll() {
        return roleDao.getAll();
    }

    public void insert(Role role) {
        roleDao.insert(role);
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public void delete(Long id) {
        roleDao.delete(id);
    }
}
