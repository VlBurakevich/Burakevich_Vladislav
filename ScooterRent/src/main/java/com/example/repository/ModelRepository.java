package com.example.repository;

import com.example.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    boolean existsByModelName(String modelName);
}
