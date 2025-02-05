package org.senla.repository;

import jakarta.validation.constraints.NotBlank;
import org.senla.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User getReferenceByUsername(@NotBlank(message = "Username cannot be empty") String username);
}
