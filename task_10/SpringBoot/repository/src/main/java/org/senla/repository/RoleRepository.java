package org.senla.repository;

import org.senla.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends GenericRepository<Role, Long> {

    public RoleRepository() {
        super(Role.class);
    }
}
