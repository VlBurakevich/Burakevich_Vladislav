package com.example.dto.location;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDto {

    @NotNull(message = "latitude cannot be null")
    @Schema(description = "Широта", example = "55.7558")
    private Double latitude;

    @NotNull(message = "longitude cannot be null")
    @Schema(description = "Долгота", example = "37.6173")
    private Double longitude;
}
