package com.example.controller;

import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleInfoDto;
import com.example.dto.vehicle.VehicleListDto;
import com.example.service.VehicleService;
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
@RequestMapping("/api/vehicles")
@Tag(name = "Vehicle API", description = "Управление транспортными средствами")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить список транспортных средств", description = "Возвращает список транспортных средств с пагинацией.")
    public VehicleListDto getVehicles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return vehicleService.getVehicles(page, size);
    }

    @GetMapping("/by-rental-point/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить транспортные средства по точке аренды", description = "Возвращает список транспортных средств по идентификатору точки аренды.")
    public VehicleListDto getVehiclesByRentalPointId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return vehicleService.getVehiclesByRentalPointId(id, page, size);
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Получить детали транспортного средства", description = "Возвращает детальную информацию о транспортном средстве.")
    public VehicleInfoDto getVehicleDetails(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return vehicleService.getVehicleDetails(id, page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать транспортное средство", description = "Создает новое транспортное средство.")
    public VehicleDto createVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        return vehicleService.createVehicle(vehicleDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить транспортное средство", description = "Обновляет существующее транспортное средство по его идентификатору.")
    public VehicleDto updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleDto vehicleDto
    ) {
        return vehicleService.updateVehicle(id, vehicleDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить транспортное средство", description = "Удаляет транспортное средство по его идентификатору.")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}
