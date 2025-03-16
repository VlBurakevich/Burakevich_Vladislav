package com.example.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalPointHierarchyDto {

    @NotNull(message = "mainRentalPoint cannot be null")
    @Schema(description = "Основная точка аренды")
    private RentalPointDto mainRentalPoint;

    @Schema(description = "Список вторичных точек аренды")
    private List<RentalPointDto> secondaryRentalPoints;
}
