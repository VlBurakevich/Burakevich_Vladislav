package com.example.repository;

import com.example.entity.RentalPoint;
import com.example.enums.PointTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalPointRepository extends JpaRepository<RentalPoint, Long> {
    boolean existsByPointName(String pointName);

    Page<RentalPoint> findRentalPointsByPointType(Pageable pageable, PointTypeEnum pointType);

    List<RentalPoint> findRentalPointsByParentPointId(Long id);
}
