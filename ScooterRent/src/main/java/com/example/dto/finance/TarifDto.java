package com.example.dto.finance;

import com.example.enums.TarifTypeEnum;
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
public class TarifDto {

    @Schema(description = "Идентификатор тарифа", example = "1")
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @Schema(description = "Название тарифа", example = "Стандартный")
    private String name;

    @NotNull(message = "tarifType cannot be empty")
    @Schema(description = "Тип тарифа", example = "HOURLY")
    private TarifTypeEnum type;

    @NotNull(message = "basePrice cannot be empty")
    @Schema(description = "Базовая цена", example = "100.00")
    private BigDecimal basePrice;

    @NotNull(message = "unitTime cannot be empty")
    @Schema(description = "Единица времени", example = "60")
    private Integer unitTime;

    @NotNull(message = "transportTypeId cannot be empty")
    @Schema(description = "Идентификатор типа транспорта", example = "1")
    private Long transportTypeId;
}