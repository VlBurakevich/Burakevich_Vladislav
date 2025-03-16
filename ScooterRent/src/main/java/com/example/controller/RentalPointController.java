package com.example.controller;

import com.example.dto.rental.RentalPointDto;
import com.example.dto.rental.RentalPointHierarchyDto;
import com.example.dto.rental.RentalPointInfoDto;
import com.example.service.RentalPointService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rental-points")
@Tag(name = "Rental Point API", description = "Управление точками аренды")
public class RentalPointController {
    private final RentalPointService rentalPointService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить список точек аренды", description = "Возвращает список точек аренды с пагинацией.")
    public ResponseEntity<List<RentalPointDto>> getRentalPoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return rentalPointService.getRentalPoints(page, size);
    }

    @GetMapping("/top-level")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить топовые точки аренды", description = "Возвращает иерархичесий список точек аренды с пагинацией по главным точкам.")
    public ResponseEntity<List<RentalPointHierarchyDto>> getTopLevelRentalPoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return rentalPointService.getTopLevelRentalPoints(page, size);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить информацию о точке аренды", description = "Возвращает информацию о точке аренды по её идентификатору.")
    public ResponseEntity<RentalPointInfoDto> getRentalPointInfoById(@PathVariable Long id) {
        return rentalPointService.getRentalPointInfoById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать точку аренды", description = "Создает новую точку аренды.")
    public ResponseEntity<RentalPointDto> createRentalPoint(@Valid @RequestBody RentalPointDto rentalPointDto) {

        return rentalPointService.createRentalPoint(rentalPointDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить точку аренды", description = "Обновляет существующую точку аренды по её идентификатору.")
    public ResponseEntity<RentalPointDto> updateRentalPoint(
            @PathVariable Long id,
            @Valid @RequestBody RentalPointDto rentalPointDto
    ) {
        return rentalPointService.updateRentalPoint(id, rentalPointDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить точку аренды", description = "Удаляет точку аренды по её идентификатору.")
    public ResponseEntity<Void> deleteRentalPoint(@PathVariable Long id) {
        return rentalPointService.deleteRentalPoint(id);
    }
}
