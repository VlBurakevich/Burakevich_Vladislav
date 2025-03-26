package com.example.repository;

import com.example.entity.TransportType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportTypeRepository extends JpaRepository<TransportType, Long> {
    boolean existsByTypeName(String typeName);
}
