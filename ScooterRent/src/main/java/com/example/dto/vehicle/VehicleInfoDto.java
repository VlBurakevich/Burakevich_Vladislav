package com.example.dto.vehicle;

import com.example.dto.rental.RentalShortInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleInfoDto {

    @Schema(description = "Идентификатор транспортного средства", example = "1")
    private Long id;

    @NotBlank(message = "serialNumber cannot be null")
    @Schema(description = "Серийный номер", example = "SN123456")
    private String serialNumber;

    @NotBlank(message = "modelName cannot be null")
    @Schema(description = "Название модели", example = "Tesla Model S")
    private String modelName;

    @NotNull(message = "batteryLevel cannot be null")
    @Schema(description = "Уровень заряда батареи", example = "80")
    private int batteryLevel;

    @NotNull(message = "mileage cannot be null")
    @Schema(description = "Пробег", example = "1000")
    private int mileage;

    @Schema(description = "Список краткой информации об арендах")
    private List<RentalShortInfoDto> rentals;
}
