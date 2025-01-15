package org.senla.repository;

import org.senla.di.annotations.Component;
import org.senla.entity.Credential;

@Component
public class CredentialRepository extends GenericRepository<Credential, Long> {

    public CredentialRepository() {
        super(Credential.class);
    }
}
