package com.example.controller;

import com.example.dto.vehicle.ModelDto;
import com.example.service.ModelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@AllArgsConstructor
@RequestMapping("/api/vehicle_models")
public class ModelController {

    private final ModelService vehicleModelService;

    @GetMapping
    public ResponseEntity<List<ModelDto>> getVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return vehicleModelService.getModelVehicles(page, size);
    }

    @PostMapping
    public ResponseEntity<ModelDto> createTarif(@Valid @RequestBody ModelDto vehicleModelDto) {
        return vehicleModelService.createModelVehicle(vehicleModelDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModelDto> updateTarif(
            @PathVariable Long id,
            @Valid @RequestBody ModelDto vehicleModelDto
    ) {
        return vehicleModelService.updateModelVehicle(id, vehicleModelDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        return vehicleModelService.deleteModelVehicle(id);
    }
}
