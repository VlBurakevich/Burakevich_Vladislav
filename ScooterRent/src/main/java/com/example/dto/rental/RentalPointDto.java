package com.example.dto.rental;

import com.example.dto.location.LocationDto;
import com.example.enums.PointTypeEnum;
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
public class RentalPointDto {

    @Schema(description = "Идентификатор точки аренды", example = "1")
    private Long id;

    @NotBlank(message = "pointName cannot be empty")
    @Schema(description = "Название точки аренды", example = "Точка 1")
    private String pointName;

    @NotNull(message = "location cannot be null")
    @Schema(description = "Местоположение точки")
    private LocationDto location;

    @NotNull(message = "capacity cannot be null")
    @Schema(description = "Вместимость точки", example = "10")
    private Integer capacity;

    @NotNull(message = "pointType cannot be null")
    @Schema(description = "Тип точки", example = "MAIN")
    private PointTypeEnum pointType;

    @NotNull(message = "parentPointId cannot be null")
    @Schema(description = "Идентификатор родительской точки", example = "2")
    private Long parentPointId;
}
