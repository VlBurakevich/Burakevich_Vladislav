package com.example.repository;

import com.example.enitity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User getReferenceByUsername(@NotBlank(message = "Username cannot be empty") String username);
}
