package org.senla.service;

import org.senla.dao.RoleDao;
import org.senla.entity.Role;

import java.util.List;

public class RoleService {
    private final RoleDao roleDao;

    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

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
