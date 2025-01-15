package org.senla.repository;

import org.senla.di.annotations.Component;
import org.senla.entity.Role;

@Component
public class RoleRepository extends GenericRepository<Role, Long> {

    public RoleRepository() {
        super(Role.class);
    }
}
