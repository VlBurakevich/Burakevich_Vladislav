package com.example.controller;

import com.example.dto.rental.RentalEndRequestDto;
import com.example.dto.rental.RentalEndResponseDto;
import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.rental.RentalStartDto;
import com.example.service.RentalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<RentalShortInfoDto>> getAllRentals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return rentalService.getAllRentals(page, size);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRental(@RequestParam Long id) {
        return rentalService.deleteRental(id);
    }

    @PostMapping("/start")
    public ResponseEntity<Long> startRental(@Valid @RequestBody RentalStartDto rentalStartDto) {
        return rentalService.startRental(rentalStartDto);
    }

    @PostMapping("/end")
    public ResponseEntity<RentalEndResponseDto> endRental(@Valid @RequestParam RentalEndRequestDto rentalEndRequestDto) {
        return rentalService.endRental(rentalEndRequestDto);
    }
}
