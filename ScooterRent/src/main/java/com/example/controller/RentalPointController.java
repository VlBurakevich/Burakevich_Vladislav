package com.example.controller;

import com.example.dto.rental.RentalPointDto;
import com.example.dto.rental.RentalPointHierarchyDto;
import com.example.dto.rental.RentalPointInfoDto;
import com.example.service.RentalPointService;
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
@RequestMapping("/api/rental-points")
public class RentalPointController {
    private RentalPointService rentalPointService;

    @GetMapping
    public ResponseEntity<List<RentalPointDto>> getRentalPoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return rentalPointService.getRentalPoints(page, size);
    }

    @GetMapping("/top-level")
    public ResponseEntity<List<RentalPointHierarchyDto>> getTopLevelRentalPoints(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return rentalPointService.getTopLevelRentalPoints(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalPointInfoDto> getRentalPointInfoById(@PathVariable Long id) {
        return rentalPointService.getRentalPointInfoById(id);
    }

    @PostMapping
    public ResponseEntity<RentalPointDto> createRentalPoint(@Valid @RequestBody RentalPointDto rentalPointDto) {
        return rentalPointService.createRentalPoint(rentalPointDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalPointDto> updateRentalPoint(
            @PathVariable Long id,
            @Valid @RequestBody RentalPointDto rentalPointDto
    ) {
        return rentalPointService.updateRentalPoint(id, rentalPointDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalPoint(@PathVariable Long id) {
        return rentalPointService.deleteRentalPoint(id);
    }
}
