package com.example.repository;

import com.example.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsBySerialNumber(String serialNumber);

    Page<Vehicle> findAllByRentalPointId(Long id, Pageable pageable);

    List<Vehicle> findAllByRentalPointId(Long id);
}
