package org.senla.service;

import org.senla.dao.CredentialsDao;
import org.senla.entity.Credential;

import java.util.List;

public class CredentialsService {
    private final CredentialsDao credentialsDao;

    public CredentialsService(CredentialsDao credentialsDao) {
        this.credentialsDao = credentialsDao;
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
