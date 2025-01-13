package org.senla.service;

import lombok.AllArgsConstructor;
import org.senla.entity.Role;
import org.senla.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role getById(Long id) {
        return roleRepository.getById(id);
    }

    public List<Role> getAll() {
        return roleRepository.fetchLimitedRandom(20);
    }

    public void insert(Role role) {
        roleRepository.save(role);
    }

    public void update(Role role) {
        roleRepository.update(role);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
