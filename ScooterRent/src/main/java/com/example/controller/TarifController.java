package com.example.controller;

import com.example.dto.finance.TarifDto;
import com.example.service.TarifService;
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
@RequestMapping("/api/tariffs")
public class TarifController {

    private TarifService tarifService;

    @GetMapping
    public ResponseEntity<List<TarifDto>> getTarifs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return tarifService.getTarifs(page, size);
    }

    @PostMapping
    public ResponseEntity<TarifDto> createTarif(@Valid @RequestBody TarifDto tarifDto) {
        return tarifService.createTarif(tarifDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifDto> updateTarif(
            @PathVariable Long id,
            @Valid @RequestBody TarifDto tarifDto
    ) {
        return tarifService.updateTarif(id, tarifDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        return tarifService.deleteTarif(id);
    }
}
