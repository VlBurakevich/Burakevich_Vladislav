package com.example.dto.vehicle;

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
public class TransportTypeDto {

    @Schema(description = "Идентификатор типа транспорта", example = "1")
    private Long id;

    @NotBlank(message = "typeName cannot be empty")
    @Schema(description = "Название типа транспорта", example = "Электромобиль")
    private String typeName;

    @NotNull(message = "basePrice cannot be empty")
    @Schema(description = "Базовая цена", example = "50.00")
    private BigDecimal basePrice;
}