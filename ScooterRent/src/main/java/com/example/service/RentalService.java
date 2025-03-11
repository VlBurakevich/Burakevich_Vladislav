package com.example.service;

import com.example.dto.rental.RentalEndRequestDto;
import com.example.dto.rental.RentalEndResponseDto;
import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.rental.RentalStartDto;
import com.example.entity.Discount;
import com.example.entity.Rental;
import com.example.entity.RentalCost;
import com.example.entity.RentalPoint;
import com.example.entity.Tarif;
import com.example.entity.Vehicle;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.mapper.RentalShortInfoMapper;
import com.example.repository.DiscountRepository;
import com.example.repository.RentalCostRepository;
import com.example.repository.RentalPointRepository;
import com.example.repository.RentalRepository;
import com.example.repository.TarifRepository;
import com.example.repository.VehicleRepository;
import com.example.util.AuthUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RentalService {
    private final VehicleRepository vehicleRepository;
    private final RentalPointRepository rentalPointRepository;
    private final TarifRepository tarifRepository;
    private final DiscountRepository discountRepository;
    private final RentalRepository rentalRepository;
    private final RentalCostCalculator rentalCostCalculator;
    private final RentalShortInfoMapper rentalShortInfoMapper;
    private final RentalCostRepository rentalCostRepository;

    public ResponseEntity<List<RentalShortInfoDto>> getAllRentals(int page, int size) {
        Page<Rental> rentalPage = rentalRepository.findAllWithRelations(PageRequest.of(page, size));
        List<RentalShortInfoDto> rental = rentalPage.getContent()
                .stream()
                .map(rentalShortInfoMapper::entityToDto)
                .toList();

        return ResponseEntity.ok(rental);
    }

    public ResponseEntity<List<RentalShortInfoDto>> getAllRentalsByUserId(int page, int size, Long userId) {
        Page<Rental> rentals = rentalRepository.findByUserId(userId, PageRequest.of(page, size));
        List<RentalShortInfoDto> rentalDtos = rentals.stream()
                .map(rentalShortInfoMapper::entityToDto)
                .toList();
        return ResponseEntity.ok(rentalDtos);
    }

    public ResponseEntity<List<RentalShortInfoDto>> getAllRentalsByVehicleId(int page, int size, Long vehicleId) {
        Page<Rental> rentals = rentalRepository.findByVehicleId(vehicleId, PageRequest.of(page, size));
        List<RentalShortInfoDto> rentalDtos = rentals.stream()
                .map(rentalShortInfoMapper::entityToDto)
                .toList();
        return ResponseEntity.ok(rentalDtos);
    }

    public ResponseEntity<Void> deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new DeleteException(Rental.class.getSimpleName());
        }
        rentalRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Long> startRental(RentalStartDto rentalStartDto) {
        Vehicle vehicle = vehicleRepository.findById(rentalStartDto.getVehicleId())
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));
        RentalPoint startPoint = rentalPointRepository.findById(rentalStartDto.getStartPointId())
                .orElseThrow(() -> new GetException(RentalPoint.class.getSimpleName()));
        Tarif tarif = tarifRepository.findById(rentalStartDto.getTarifId())
                .orElseThrow(() -> new GetException(Tarif.class.getSimpleName()));
        Discount discount = discountRepository.findById(rentalStartDto.getDiscountId())
                .orElseThrow(() -> new GetException(Discount.class.getSimpleName()));

        Rental rental = new Rental();
        rental.setUser(AuthUtil.getAuthenticatedUser());
        rental.setVehicle(vehicle);
        rental.setStartPoint(startPoint);
        rental.setCreatedAt(rentalStartDto.getStartTime() != null ?
                rentalStartDto.getStartTime() : LocalDateTime.now() );

        RentalCost rentalCost = new RentalCost();
        rentalCost.setRental(rental);
        rentalCost.setStartTime(rental.getCreatedAt());
        rentalCost.setEndTime(rental.getCreatedAt());
        rentalCost.setTarif(tarif);
        rentalCost.setDiscount(discount);
        rentalCost.setTotalCost(BigDecimal.ZERO);

        rentalCostRepository.save(rentalCost);

        rental.setRentalCost(rentalCost);
        rentalRepository.save(rental);

        return ResponseEntity.ok(rental.getId());
    }

    @Transactional
    public ResponseEntity<RentalEndResponseDto> endRental(RentalEndRequestDto rentalEndRequestDto) {
        Rental rental = rentalRepository.findById(rentalEndRequestDto.getRentalId())
                .orElseThrow(() -> new GetException(Rental.class.getSimpleName()));

        rental.setEndPoint(rentalPointRepository.findById(rentalEndRequestDto.getEndPointId())
                .orElseThrow(() -> new GetException(RentalPoint.class.getSimpleName())));

        RentalCost rentalCost = rental.getRentalCost();
        if (rentalCost == null) {
            throw new GetException(RentalCost.class.getSimpleName());
        }
        rentalCost.setEndTime(rentalEndRequestDto.getEndTime() != null ?
                rentalEndRequestDto.getEndTime() : LocalDateTime.now());

        rentalCost.setTotalCost(rentalCostCalculator.calculateTotalCost(rentalCost));

        Vehicle vehicle = rental.getVehicle();
        vehicle.setBatteryLevel(rentalEndRequestDto.getBatteryLevel());
        vehicleRepository.save(vehicle);

        rentalRepository.save(rental);

        return ResponseEntity.ok(new RentalEndResponseDto(
                rentalCost.getStartTime(),
                rentalCost.getEndTime(),
                rentalCost.getTarif().getName(),
                rentalCost.getDiscount() != null ? rentalCost.getDiscount().getName() : null,
                rentalCost.getTotalCost()
        ));
    }
}
