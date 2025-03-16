package com.example.dto.vehicle;

import com.example.enums.VehiclesStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {

    @Schema(description = "Идентификатор транспортного средства", example = "1")
    private Long id;

    @NotNull(message = "modelId cannot be null")
    @Schema(description = "Идентификатор модели", example = "2")
    private Long modelId;

    @NotBlank(message = "serialNumber cannot be null")
    @Schema(description = "Серийный номер", example = "SN123456")
    private String serialNumber;

    @NotNull(message = "batteryLevel cannot be null")
    @Schema(description = "Уровень заряда батареи", example = "80")
    private int batteryLevel;

    @NotNull(message = "status cannot be null")
    @Schema(description = "Статус транспортного средства", example = "AVAILABLE")
    private VehiclesStatusEnum status;

    @NotNull(message = "mileage cannot be null")
    @Schema(description = "Пробег", example = "1000")
    private int mileage;

    @NotNull(message = "rentalPointId cannot be null")
    @Schema(description = "Идентификатор точки аренды", example = "3")
    private Long rentalPointId;
}
