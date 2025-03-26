package com.example.controller;

import com.example.dto.finance.DiscountDto;
import com.example.dto.finance.DiscountListDto;
import com.example.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discounts")
@Tag(name = "Discount API", description = "Управление скидками")
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Получить список скидок", description = "Возвращает список скидок с пагинацией.")
    public DiscountListDto getDiscounts(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return discountService.getDiscounts(page, size);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Создать скидку", description = "Создает новую скидку.")
    public DiscountDto createDiscount(@Valid @RequestBody DiscountDto discountDto) {
        return discountService.createDiscount(discountDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Обновить скидку", description = "Обновляет существующую скидку по её идентификатору.")
    public DiscountDto updateDiscount(
            @PathVariable Long id,
            @Valid @RequestBody DiscountDto discountDto) {
        return discountService.updateDiscount(id, discountDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Удалить скидку", description = "Удаляет скидку по её идентификатору.")
    public void deleteDiscount(@PathVariable Long id) {
        discountService.deleteDiscount(id);
    }
}
