package org.senla.service;

import org.senla.dao.CredentialsDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.di.annotations.AfterInjectConstructor;
import org.senla.entity.Credential;

import java.util.List;

@Component
public class CredentialsService {
    @Autowired
    private CredentialsDao credentialsDao;

    @AfterInjectConstructor
    public void getById() {
        credentialsDao.getById(1L);
    }

    public Credential getById(Long id) {
        return credentialsDao.getById(id);
    }

    public List<Credential> getAll() {
        return credentialsDao.getAll();
    }

    public void insert(Credential credential) {
        credentialsDao.insert(credential);
    }

    public void update(Credential credential) {
        credentialsDao.update(credential);
    }

    public void delete(Long id) {
        credentialsDao.delete(id);
    }
}
