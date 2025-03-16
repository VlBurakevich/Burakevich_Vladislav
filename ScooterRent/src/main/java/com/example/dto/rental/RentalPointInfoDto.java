package com.example.dto.rental;

import com.example.dto.location.LocationDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.enums.PointTypeEnum;
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
public class RentalPointInfoDto {

    @Schema(description = "Идентификатор точки аренды", example = "1")
    private Long id;

    @NotBlank(message = "pointName cannot be null")
    @Schema(description = "Название точки аренды", example = "Точка 1")
    private String pointName;

    @NotNull(message = "location cannot be null")
    @Schema(description = "Местоположение точки")
    private LocationDto location;

    @NotNull(message = "pointType cannot be null")
    @Schema(description = "Тип точки", example = "MAIN")
    private PointTypeEnum pointType;

    @NotNull(message = "vehicles cannot be null")
    @Schema(description = "Список транспортных средств на точке")
    private List<VehicleDto> vehicles;
}
