package com.example.controller;

import com.example.dto.vehicle.TransportTypeDto;
import com.example.dto.vehicle.TransportTypeListDto;
import com.example.service.TransportTypeService;
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
@RequestMapping("/api/transport-types")
@Tag(name = "Transport Type API", description = "Управление типами транспорта")
public class TransportTypeController {

    private final TransportTypeService transportTypeService;

    @GetMapping
    @Operation(summary = "Получить список типов транспорта", description = "Возвращает список типов транспорта с пагинацией.")
    public TransportTypeListDto getDiscounts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return transportTypeService.getTransportTypes(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать тип транспорта", description = "Создает новый тип транспорта.")
    public TransportTypeDto createDiscount(@Valid @RequestBody TransportTypeDto transportTypeDto) {
        return transportTypeService.createTransportType(transportTypeDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить тип транспорта", description = "Обновляет существующий тип транспорта по его идентификатору.")
    public TransportTypeDto updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody TransportTypeDto transportTypeDto) {
        return transportTypeService.updateTransportType(id, transportTypeDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить тип транспорта", description = "Удаляет тип транспорта по его идентификатору.")
    public void deleteDiscount(@PathVariable Long id) {
        transportTypeService.deleteTransportType(id);
    }
}
