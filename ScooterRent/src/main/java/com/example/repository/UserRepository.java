package com.example.repository;

import com.example.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    User getReferenceByUsername(@NotBlank(message = "Username cannot be empty") String username);
}
