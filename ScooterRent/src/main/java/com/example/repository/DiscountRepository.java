package com.example.repository;

import com.example.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    boolean existsByName(String name);
}
