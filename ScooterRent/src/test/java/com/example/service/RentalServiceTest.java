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
import com.example.entity.User;
import com.example.entity.Vehicle;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.mapper.RentalShortInfoMapper;
import com.example.repository.DiscountRepository;
import com.example.repository.RentalPointRepository;
import com.example.repository.RentalRepository;
import com.example.repository.TarifRepository;
import com.example.repository.UserRepository;
import com.example.repository.VehicleRepository;
import com.example.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private RentalPointRepository rentalPointRepository;

    @Mock
    private TarifRepository tarifRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private RentalCostCalculator rentalCostCalculator;

    @Mock
    private RentalShortInfoMapper rentalShortInfoMapper;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetAllRentals() {
        Rental rental = new Rental();
        rental.setId(1L);

        RentalShortInfoDto rentalShortInfoDto = new RentalShortInfoDto();
        rentalShortInfoDto.setId(1L);

        Page<Rental> rentalPage = new PageImpl<>(Collections.singletonList(rental));
        when(rentalRepository.findAllWithRelations(any(PageRequest.class))).thenReturn(rentalPage);
        when(rentalShortInfoMapper.entityToDto(rental)).thenReturn(rentalShortInfoDto);

        ResponseEntity<List<RentalShortInfoDto>> response = rentalService.getAllRentals(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(rentalShortInfoDto, response.getBody().getFirst());

        verify(rentalRepository, times(1)).findAllWithRelations(any(PageRequest.class));
        verify(rentalShortInfoMapper, times(1)).entityToDto(rental);
    }

    @Test
    void testGetAllRentalsByUserId() {
        Rental rental = new Rental();
        rental.setId(1L);

        RentalShortInfoDto rentalShortInfoDto = new RentalShortInfoDto();
        rentalShortInfoDto.setId(1L);

        Page<Rental> rentalPage = new PageImpl<>(Collections.singletonList(rental));
        when(rentalRepository.findByUserId(1L, PageRequest.of(0, 10))).thenReturn(rentalPage);
        when(rentalShortInfoMapper.entityToDto(rental)).thenReturn(rentalShortInfoDto);

        ResponseEntity<List<RentalShortInfoDto>> response = rentalService.getAllRentalsByUserId(0, 10, 1L);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(rentalShortInfoDto, response.getBody().getFirst());

        verify(rentalRepository, times(1)).findByUserId(1L, PageRequest.of(0, 10));
        verify(rentalShortInfoMapper, times(1)).entityToDto(rental);
    }

    @Test
    void testGetAllRentalsByVehicleId() {
        Rental rental = new Rental();
        rental.setId(1L);

        RentalShortInfoDto rentalShortInfoDto = new RentalShortInfoDto();
        rentalShortInfoDto.setId(1L);

        Page<Rental> rentalPage = new PageImpl<>(Collections.singletonList(rental));
        when(rentalRepository.findByVehicleId(1L, PageRequest.of(0, 10))).thenReturn(rentalPage);
        when(rentalShortInfoMapper.entityToDto(rental)).thenReturn(rentalShortInfoDto);

        ResponseEntity<List<RentalShortInfoDto>> response = rentalService.getAllRentalsByVehicleId(0, 10, 1L);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(rentalShortInfoDto, response.getBody().getFirst());

        verify(rentalRepository, times(1)).findByVehicleId(1L, PageRequest.of(0, 10));
        verify(rentalShortInfoMapper, times(1)).entityToDto(rental);
    }

    @Test
    void testDeleteRental_Success() {
        Long id = 1L;
        when(rentalRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = rentalService.deleteRental(id);

        assertTrue(response.getStatusCode().is2xxSuccessful());

        verify(rentalRepository, times(1)).existsById(id);
        verify(rentalRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteRental_NotFound() {
        Long id = 1L;
        when(rentalRepository.existsById(id)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> rentalService.deleteRental(id));
        assertTrue(exception.getMessage().contains(Rental.class.getSimpleName()));

        verify(rentalRepository, times(1)).existsById(id);
        verify(rentalRepository, never()).deleteById(any());
    }

    @Test
    void testStartRental_Success() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = Mockito.mockStatic(AuthUtil.class)) {

            RentalStartDto rentalStartDto = new RentalStartDto();
            rentalStartDto.setVehicleId(1L);
            rentalStartDto.setStartPointId(2L);
            rentalStartDto.setTarifId(3L);
            rentalStartDto.setDiscountId(4L);

            Vehicle vehicle = new Vehicle();
            vehicle.setId(1L);

            RentalPoint startPoint = new RentalPoint();
            startPoint.setId(2L);

            Tarif tarif = new Tarif();
            tarif.setId(3L);
            tarif.setBasePrice(new BigDecimal("10.00"));

            Discount discount = new Discount();
            discount.setId(4L);
            discount.setValue(new BigDecimal("5.00"));

            User user = new User();
            user.setId(1L);
            user.setBalance(new BigDecimal("1000.00"));

            when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
            when(rentalPointRepository.findById(2L)).thenReturn(Optional.of(startPoint));
            when(tarifRepository.findById(3L)).thenReturn(Optional.of(tarif));
            when(discountRepository.findById(4L)).thenReturn(Optional.of(discount));

            mockedAuthUtil.when(AuthUtil::getAuthenticatedUser).thenReturn(user);

            when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> {
                Rental rental = invocation.getArgument(0);
                rental.setId(1L);
                return rental;
            });

            ResponseEntity<Long> response = rentalService.startRental(rentalStartDto);

            assertNotNull(response.getBody());
            assertEquals(1L, response.getBody());

            verify(vehicleRepository, times(1)).findById(1L);
            verify(rentalPointRepository, times(1)).findById(2L);
            verify(tarifRepository, times(1)).findById(3L);
            verify(discountRepository, times(1)).findById(4L);
            verify(rentalRepository, times(1)).save(any(Rental.class));
        }
    }

    @Test
    void testEndRental_Success() {
        Long id = 1L;
        RentalEndRequestDto rentalEndRequestDto = new RentalEndRequestDto();
        rentalEndRequestDto.setEndPointId(2L);
        rentalEndRequestDto.setEndTime(LocalDateTime.now());
        rentalEndRequestDto.setBatteryLevel(80);

        Rental rental = new Rental();
        rental.setId(id);

        User user = new User();
        user.setBalance(new BigDecimal("1000.00"));
        rental.setUser(user);

        RentalPoint endPoint = new RentalPoint();
        endPoint.setId(2L);

        RentalCost rentalCost = new RentalCost();
        rentalCost.setStartTime(LocalDateTime.now());
        rentalCost.setTarif(new Tarif());
        rentalCost.setDiscount(new Discount());

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);

        rental.setRentalCost(rentalCost);
        rental.setVehicle(vehicle);

        when(rentalRepository.findById(id)).thenReturn(Optional.of(rental));
        when(rentalPointRepository.findById(2L)).thenReturn(Optional.of(endPoint));
        when(rentalCostCalculator.calculateTotalCost(rentalCost)).thenReturn(BigDecimal.valueOf(1500.00));

        ResponseEntity<RentalEndResponseDto> response = rentalService.endRental(id, rentalEndRequestDto);

        assertNotNull(response.getBody());
        assertEquals(rentalCost.getStartTime(), response.getBody().getStartTime());
        assertEquals(rentalEndRequestDto.getEndTime(), response.getBody().getEndTime());
        assertEquals(rentalCost.getTarif().getName(), response.getBody().getTarifName());
        assertEquals(rentalCost.getDiscount().getName(), response.getBody().getDiscountName());
        assertEquals(BigDecimal.valueOf(1500.00), response.getBody().getRentalPrice());

        verify(rentalRepository, times(1)).findById(id);
        verify(rentalPointRepository, times(1)).findById(2L);
        verify(rentalCostCalculator, times(1)).calculateTotalCost(rentalCost);
    }

    @Test
    void testEndRental_NotFound() {
        Long id = 1L;
        RentalEndRequestDto rentalEndRequestDto = new RentalEndRequestDto();
        rentalEndRequestDto.setEndPointId(2L);

        when(rentalRepository.findById(id)).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> rentalService.endRental(id, rentalEndRequestDto));
        assertTrue(exception.getMessage().contains(Rental.class.getSimpleName()));

        verify(rentalRepository, times(1)).findById(id);
        verify(rentalPointRepository, never()).findById(any());
    }
}
