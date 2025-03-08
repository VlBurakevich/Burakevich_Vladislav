package com.example.service;

import com.example.dto.rental.RentalPointDto;
import com.example.dto.rental.RentalPointInfoDto;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.mapper.RentalPointInfoMapper;
import com.example.mapper.RentalPointMapper;
import com.example.repository.RentalPointRepository;
import com.example.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RentalPointService {

    private final RentalPointRepository rentalPointRepository;
    private final VehicleRepository vehicleRepository;

    public ResponseEntity<List<RentalPointDto>> getRentalPoints(int page, int size) {
        Page<RentalPoint> rentalPointPage = rentalPointRepository.findAll(PageRequest.of(page, size));
        List<RentalPointDto> rentalPoints = rentalPointPage.getContent()
                .stream()
                .map(RentalPointMapper.INSTANCE::entityToDto)
                .toList();

        return ResponseEntity.ok(rentalPoints);
    }

    public ResponseEntity<List<RentalPointDto>> getTopLevelRentalPoints(int page, int size) {

    }

    public ResponseEntity<RentalPointInfoDto> getRentalPointInfoById(Long id) {
        RentalPoint rentalPoint = rentalPointRepository.findById(id).
                orElseThrow(() -> new GetException(RentalPoint.class.getSimpleName()));
        List<Vehicle> vehicles = vehicleRepository.findAllByRentalPointId(id);
        return ResponseEntity.ok(RentalPointInfoMapper.INSTANCE.toRentalPointInfoDto(rentalPoint, vehicles));
    }

    @Transactional
    public ResponseEntity<RentalPointDto> createRentalPoint(RentalPointDto rentalPointDto) {
        if (rentalPointRepository.existsByPointName(rentalPointDto.getPointName())) {
            throw new CreateException(RentalPoint.class.getSimpleName());
        }
        RentalPoint rentalPoint = RentalPointMapper.INSTANCE.dtoToEntity(rentalPointDto);

        rentalPoint.setParentPoint(
                Optional.ofNullable(rentalPointDto.getParentPointId())
                        .flatMap(rentalPointRepository::findById)
                        .orElse(null)
        );

        return ResponseEntity.ok(
                RentalPointMapper.INSTANCE.entityToDto(rentalPointRepository.save(rentalPoint))
        );
    }

    @Transactional
    public ResponseEntity<RentalPointDto> updateRentalPoint(Long id, @Valid RentalPointDto rentalPointDto) {
        RentalPoint existingRentalPoint = rentalPointRepository.findById(id)
                .orElseThrow(() -> new UpdateException(RentalPoint.class.getSimpleName()));
        rentalPointDto.setId(id);
        RentalPointMapper.INSTANCE.updateEntityFromDto(rentalPointDto, existingRentalPoint);
        RentalPoint rentalPoint = rentalPointRepository.save(existingRentalPoint);

        return ResponseEntity.ok(RentalPointMapper.INSTANCE.entityToDto(rentalPoint));
    }

    @Transactional
    public ResponseEntity<Void> deleteRentalPoint(Long id) {
        if (!rentalPointRepository.existsById(id)) {
            throw new DeleteException(RentalPoint.class.getSimpleName());
        }
        rentalPointRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
