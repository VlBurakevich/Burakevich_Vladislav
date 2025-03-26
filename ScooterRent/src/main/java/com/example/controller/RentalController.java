package com.example.controller;

import com.example.dto.rental.RentalEndRequestDto;
import com.example.dto.rental.RentalEndResponseDto;
import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.rental.RentalShortInfoListDto;
import com.example.dto.rental.RentalStartDto;
import com.example.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
@Tag(name = "Rental API", description = "Управление арендами")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Получить список аренд", description = "Возвращает список аренд с пагинацией.")
    public RentalShortInfoListDto getAllRentals(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return rentalService.getAllRentals(page, size);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить аренду", description = "Удаляет аренду по её идентификатору.")
    public void deleteRental(@RequestParam Long id) {
        rentalService.deleteRental(id);
    }

    @PostMapping("/start")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Начать аренду", description = "Создает новую аренду.")
    public Long startRental(@Valid @RequestBody RentalStartDto rentalStartDto) {
        return rentalService.startRental(rentalStartDto);
    }

    @PostMapping("/end/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Завершить аренду", description = "Завершает аренду и возвращает итоговую информацию.")
    public RentalEndResponseDto endRental(
            @PathVariable Long id,
            @Valid @RequestParam RentalEndRequestDto rentalEndRequestDto
    ) {
        return rentalService.endRental(id, rentalEndRequestDto);
    }
}
