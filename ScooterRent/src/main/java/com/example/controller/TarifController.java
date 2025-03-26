package com.example.controller;

import com.example.dto.finance.TarifDto;
import com.example.dto.finance.TarifListDto;
import com.example.service.TarifService;
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
@RequestMapping("/api/tariffs")
@Tag(name = "Tariff API", description = "Управление тарифами")
public class TarifController {

    private final TarifService tarifService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить список тарифов", description = "Возвращает список тарифов с пагинацией.")
    public TarifListDto getTarifs(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return tarifService.getTarifs(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать тариф", description = "Создает новый тариф.")
    public TarifDto createTarif(@Valid @RequestBody TarifDto tarifDto) {
        return tarifService.createTarif(tarifDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить тариф", description = "Обновляет существующий тариф по его идентификатору.")
    public TarifDto updateTarif(
            @PathVariable Long id,
            @Valid @RequestBody TarifDto tarifDto
    ) {
        return tarifService.updateTarif(id, tarifDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить тариф", description = "Удаляет тариф по его идентификатору.")
    public void deleteTarif(@PathVariable Long id) {
        tarifService.deleteTarif(id);
    }
}
