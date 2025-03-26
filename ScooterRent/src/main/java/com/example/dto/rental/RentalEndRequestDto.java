package com.example.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalEndRequestDto {

    @NotNull(message = "endPointId cannot be null")
    @Schema(description = "Идентификатор конечной точки", example = "2")
    private Long endPointId;

    @Schema(description = "Время окончания аренды", example = "2025-03-14T17:38:48.219Z")
    private LocalDateTime endTime;

    @NotNull(message = "batteryLevel cannot be null")
    @Schema(description = "Уровень заряда батареи", example = "80")
    private Short batteryLevel;
}
