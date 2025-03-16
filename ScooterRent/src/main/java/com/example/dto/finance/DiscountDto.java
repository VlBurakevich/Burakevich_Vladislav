package com.example.dto.finance;

import com.example.enums.DiscountTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    @Schema(description = "Идентификатор скидки", example = "1")
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @Schema(description = "Название скидки", example = "Скидка 10%")
    private String name;

    @NotNull(message = "discountType cannot be empty")
    @Schema(description = "Тип скидки", example = "PERCENTAGE")
    private DiscountTypeEnum type;

    @NotNull(message = "discountAmount cannot be empty")
    @Schema(description = "Размер скидки", example = "10.00")
    private BigDecimal value;
}