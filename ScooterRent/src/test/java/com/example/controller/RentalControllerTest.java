package com.example.controller;

import com.example.dto.rental.RentalEndRequestDto;
import com.example.dto.rental.RentalEndResponseDto;
import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.rental.RentalStartDto;
import com.example.service.RentalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalControllerTest {

    @InjectMocks
    private RentalController rentalController;

    @Mock
    private RentalService rentalService;

    @Test
    void testGetAllRentals() {
        RentalShortInfoDto rentalShortInfoDto = new RentalShortInfoDto();
        rentalShortInfoDto.setId(1L);
        rentalShortInfoDto.setUsername("user123");
        rentalShortInfoDto.setTotalCost(new BigDecimal("1500.00"));
        rentalShortInfoDto.setVehicleModelName("Tesla Model S");
        rentalShortInfoDto.setCreatedAt(LocalDateTime.now());

        List<RentalShortInfoDto> rentalList = Collections.singletonList(rentalShortInfoDto);

        when(rentalService.getAllRentals(0, 10)).thenReturn(ResponseEntity.ok(rentalList));

        ResponseEntity<List<RentalShortInfoDto>> response = rentalController.getAllRentals(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(rentalList, response.getBody());
        verify(rentalService, times(1)).getAllRentals(0, 10);
    }

    @Test
    void testDeleteRental() {
        Long rentalId = 1L;
        when(rentalService.deleteRental(rentalId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = rentalController.deleteRental(rentalId);

        assertEquals(204, response.getStatusCode().value());
        verify(rentalService, times(1)).deleteRental(rentalId);
    }

    @Test
    void testStartRental() {
        RentalStartDto rentalStartDto = new RentalStartDto();
        rentalStartDto.setVehicleId(1L);
        rentalStartDto.setStartPointId(2L);
        rentalStartDto.setTarifId(3L);
        rentalStartDto.setDiscountId(4L);

        Long rentalId = 1L;
        when(rentalService.startRental(rentalStartDto)).thenReturn(ResponseEntity.ok(rentalId));

        ResponseEntity<Long> response = rentalController.startRental(rentalStartDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(rentalId, response.getBody());
        verify(rentalService, times(1)).startRental(rentalStartDto);
    }

    @Test
    void testEndRental() {
        Long rentalId = 1L;
        RentalEndRequestDto rentalEndRequestDto = new RentalEndRequestDto();
        rentalEndRequestDto.setEndPointId(2L);
        rentalEndRequestDto.setEndTime(LocalDateTime.now());
        rentalEndRequestDto.setBatteryLevel(80);

        RentalEndResponseDto rentalEndResponseDto = RentalEndResponseDto.builder()
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .tarifName("Стандартный")
                .discountName("Скидка 10%")
                .rentalPrice(new BigDecimal("1500.00"))
                .build();

        when(rentalService.endRental(rentalId, rentalEndRequestDto)).thenReturn(ResponseEntity.ok(rentalEndResponseDto));

        ResponseEntity<RentalEndResponseDto> response = rentalController.endRental(rentalId, rentalEndRequestDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(rentalEndResponseDto, response.getBody());
        verify(rentalService, times(1)).endRental(rentalId, rentalEndRequestDto);
    }
}
