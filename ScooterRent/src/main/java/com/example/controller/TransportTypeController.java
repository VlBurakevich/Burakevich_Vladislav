package com.example.controller;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.service.TransportTypeService;
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
@RequestMapping("/api/transport-types")
public class TransportTypeController {

    private final TransportTypeService transportTypeService;

    @GetMapping
    public ResponseEntity<List<TransportTypeDto>> getDiscounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return transportTypeService.getTransportTypes(page, size);
    }

    @PostMapping
    public ResponseEntity<TransportTypeDto> createDiscount(@Valid @RequestBody TransportTypeDto transportTypeDto) {
        return transportTypeService.createTransportType(transportTypeDto);
    }

    @PutMapping("{/id}")
    public ResponseEntity<TransportTypeDto> updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody TransportTypeDto transportTypeDto) {
        return transportTypeService.updateTransportType(id, transportTypeDto);
    }

    @DeleteMapping("{/id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        return transportTypeService.deleteTransportType(id);
    }
}
