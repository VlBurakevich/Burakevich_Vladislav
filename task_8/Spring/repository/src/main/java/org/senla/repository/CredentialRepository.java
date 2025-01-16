package org.senla.repository;

import org.senla.entity.Credential;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository extends GenericRepository<Credential, Long> {

    public CredentialRepository() {
        super(Credential.class);
    }
}
