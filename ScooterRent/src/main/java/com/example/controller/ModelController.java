package com.example.controller;

import com.example.dto.vehicle.ModelDto;
import com.example.service.ModelService;
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
@RequestMapping("/api/vehicle_models")
@Tag(name = "Vehicle Model API", description = "Управление моделями транспортных средств")
public class ModelController {

    private final ModelService vehicleModelService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить список моделей", description = "Возвращает список моделей транспортных средств с пагинацией.")
    public ResponseEntity<List<ModelDto>> getVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return vehicleModelService.getModelVehicles(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать модель", description = "Создает новую модель транспортного средства.")
    public ResponseEntity<ModelDto> createTarif(@Valid @RequestBody ModelDto vehicleModelDto) {
        return vehicleModelService.createModelVehicle(vehicleModelDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить модель", description = "Обновляет существующую модель транспортного средства по её идентификатору.")
    public ResponseEntity<ModelDto> updateTarif(
            @PathVariable Long id,
            @Valid @RequestBody ModelDto vehicleModelDto
    ) {
        return vehicleModelService.updateModelVehicle(id, vehicleModelDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить модель", description = "Удаляет модель транспортного средства по её идентификатору.")
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        return vehicleModelService.deleteModelVehicle(id);
    }
}
