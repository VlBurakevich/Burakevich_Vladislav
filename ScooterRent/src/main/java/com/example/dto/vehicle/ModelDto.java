package com.example.dto.vehicle;

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
public class ModelDto {

    @Schema(description = "Идентификатор модели", example = "1")
    private Long id;

    @NotBlank(message = "model cannot be empty")
    @Schema(description = "Название модели", example = "Tesla Model S")
    private String modelName;

    @NotNull(message = "max speed cannot be null")
    @Schema(description = "Максимальная скорость", example = "250")
    private Integer maxSpeed;

    @NotNull(message = "must have transportTypeId")
    @Schema(description = "Идентификатор типа транспорта", example = "1")
    private Long transportTypeId;
}
