package com.example.service;

import com.example.dto.rental.RentalEndRequestDto;
import com.example.dto.rental.RentalEndResponseDto;
import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.rental.RentalShortInfoListDto;
import com.example.dto.rental.RentalStartDto;
import com.example.entity.Discount;
import com.example.entity.Rental;
import com.example.entity.RentalCost;
import com.example.entity.RentalPoint;
import com.example.entity.Tarif;
import com.example.entity.User;
import com.example.entity.Vehicle;
import com.example.enums.VehiclesStatusEnum;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.InsufficientBalanceException;
import com.example.exceptions.PaymentRequiredException;
import com.example.mapper.RentalShortInfoMapper;
import com.example.repository.DiscountRepository;
import com.example.repository.RentalPointRepository;
import com.example.repository.RentalRepository;
import com.example.repository.TarifRepository;
import com.example.repository.UserRepository;
import com.example.repository.VehicleRepository;
import com.example.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final VehicleRepository vehicleRepository;
    private final RentalPointRepository rentalPointRepository;
    private final TarifRepository tarifRepository;
    private final DiscountRepository discountRepository;
    private final RentalRepository rentalRepository;
    private final RentalCostCalculator rentalCostCalculator;
    private final RentalShortInfoMapper rentalShortInfoMapper;
    private final UserRepository userRepository;

    public RentalShortInfoListDto getAllRentals(Integer page, Integer size) {
        Page<Rental> rentalPage = rentalRepository.findAllWithRelations(PageRequest.of(page, size));
        List<RentalShortInfoDto> rentals = rentalPage.getContent().stream()
                .map(rentalShortInfoMapper::entityToDto)
                .toList();

        return new RentalShortInfoListDto(rentals);
    }

    public List<RentalShortInfoDto> getAllRentalsByUserId(Integer page, Integer size, Long userId) {
        Page<Rental> rentals = rentalRepository.findByUserId(userId, PageRequest.of(page, size));

        return rentals.stream()
                .map(rentalShortInfoMapper::entityToDto)
                .toList();
    }

    public List<RentalShortInfoDto> getAllRentalsByVehicleId(Integer page, Integer size, Long vehicleId) {
        Page<Rental> rentals = rentalRepository.findByVehicleId(vehicleId, PageRequest.of(page, size));

        return rentals.stream()
                .map(rentalShortInfoMapper::entityToDto)
                .toList();
    }

    @Transactional
    public void deleteRental(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new DeleteException(Rental.class.getSimpleName());
        }
        rentalRepository.deleteById(id);
    }

    @Transactional
    public Long startRental(RentalStartDto rentalStartDto) {

        Vehicle vehicle = vehicleRepository.findById(rentalStartDto.getVehicleId())
                .orElseThrow(() -> new GetException(Vehicle.class.getSimpleName()));
        RentalPoint startPoint = rentalPointRepository.findById(rentalStartDto.getStartPointId())
                .orElseThrow(() -> new GetException(RentalPoint.class.getSimpleName()));
        Tarif tarif = tarifRepository.findById(rentalStartDto.getTarifId())
                .orElseThrow(() -> new GetException(Tarif.class.getSimpleName()));
        Discount discount = discountRepository.findById(rentalStartDto.getDiscountId())
                .orElseThrow(() -> new GetException(Discount.class.getSimpleName()));
        User user = AuthUtil.getAuthenticatedUser();

        checkUserBalance(user, tarif.getBasePrice());

        LocalDateTime startTime = rentalStartDto.getStartTime() != null ?
                rentalStartDto.getStartTime() : LocalDateTime.now();

        Rental rental = createRental(vehicle, startPoint, tarif, discount, user, startTime);

        vehicle.setStatus(VehiclesStatusEnum.RENTED);
        vehicle.setRentalPoint(null);

        rentalRepository.save(rental);
        return rental.getId();
    }

    private void checkUserBalance(User user, BigDecimal minimalCost) {
        if (user.getBalance().compareTo(minimalCost) < 0) {
            throw new InsufficientBalanceException(minimalCost);
        }
    }

    private Rental createRental(Vehicle vehicle, RentalPoint startPoint, Tarif tarif, Discount discount, User user, LocalDateTime startTime) {
        RentalCost rentalCost = RentalCost.builder()
                .startTime(startTime)
                .endTime(startTime)
                .tarif(tarif)
                .discount(discount)
                .totalCost(BigDecimal.ZERO)
                .build();

        return Rental.builder()
                .user(user)
                .vehicle(vehicle)
                .startPoint(startPoint)
                .createdAt(startTime)
                .rentalCost(rentalCost)
                .build();
    }

    @Transactional
    public RentalEndResponseDto endRental(Long id, RentalEndRequestDto rentalEndRequestDto) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new GetException(Rental.class.getSimpleName()));
        RentalPoint endPoint = rentalPointRepository.findById(rentalEndRequestDto.getEndPointId())
                .orElseThrow(() -> new GetException(RentalPoint.class.getSimpleName()));

        rental.setEndPoint(endPoint);

        RentalCost rentalCost = rental.getRentalCost();
        if (rentalCost == null) {
            throw new GetException(RentalCost.class.getSimpleName());
        }

        updateRentalCost(rentalCost, rentalEndRequestDto.getEndTime());

        User user = rental.getUser();
        BigDecimal newBalance = user.getBalance().subtract(rentalCost.getTotalCost());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentRequiredException(rentalCost.getTotalCost(), user.getBalance());
        }

        user.setBalance(newBalance);
        userRepository.save(user);

        Vehicle vehicle = rental.getVehicle();
        vehicle.setBatteryLevel(rentalEndRequestDto.getBatteryLevel());
        vehicle.setStatus(VehiclesStatusEnum.AVAILABLE);
        vehicle.setRentalPoint(endPoint);

        rentalRepository.save(rental);

        return createResponse(rentalCost, user);
    }

    private void updateRentalCost(RentalCost rentalCost, LocalDateTime endTime) {
        LocalDateTime rentalEndTime = endTime != null ? endTime : LocalDateTime.now();
        rentalCost.setEndTime(rentalEndTime);

        BigDecimal totalCost = rentalCostCalculator.calculateTotalCost(rentalCost);
        rentalCost.setTotalCost(totalCost);
    }

    private RentalEndResponseDto createResponse(RentalCost rentalCost, User user) {
        RentalEndResponseDto response = RentalEndResponseDto.builder()
                .startTime(rentalCost.getStartTime())
                .endTime(rentalCost.getEndTime())
                .tarifName(rentalCost.getTarif().getName())
                .discountName(rentalCost.getDiscount() != null ? rentalCost.getDiscount().getName() : null)
                .rentalPrice(rentalCost.getTotalCost())
                .build();

        if (user.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new PaymentRequiredException(rentalCost.getTotalCost(), user.getBalance());
        }
        return response;
    }
}
