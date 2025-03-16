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
public class RentalStartDto {
    @NotNull(message = "vehicleId cannot be null")
    @Schema(description = "Идентификатор транспортного средства", example = "1")
    private Long vehicleId;

    @NotNull(message = "startPointId cannot be null")
    @Schema(description = "Идентификатор начальной точки", example = "2")
    private Long startPointId;

    @Schema(description = "Время начала аренды", example = "2025-03-14T17:38:48.219Z")
    private LocalDateTime startTime;

    @NotNull(message = "tarifId cannot be null")
    @Schema(description = "Идентификатор тарифа", example = "3")
    private Long tarifId;

    @NotNull(message = "discountId cannot be null")
    @Schema(description = "Идентификатор скидки", example = "4")
    private Long discountId;
}
