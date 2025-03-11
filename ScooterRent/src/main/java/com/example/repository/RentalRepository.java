package com.example.repository;

import com.example.entity.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r " +
            "JOIN FETCH r.user " +
            "JOIN FETCH r.vehicle v " +
            "JOIN FETCH v.model")
    Page<Rental> findAllWithRelations(Pageable pageable);

    @Query("SELECT r FROM Rental r " +
            "JOIN FETCH r.user " +
            "JOIN FETCH r.vehicle v " +
            "JOIN FETCH v.model " +
            "WHERE r.user.id = :userId")
    Page<Rental> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Rental r " +
            "JOIN FETCH r.user " +
            "JOIN FETCH r.vehicle v " +
            "JOIN FETCH v.model " +
            "WHERE r.vehicle.id = :vehicleId")
    Page<Rental> findByVehicleId(@Param("vehicleId") Long vehicleId, Pageable pageable);
}
