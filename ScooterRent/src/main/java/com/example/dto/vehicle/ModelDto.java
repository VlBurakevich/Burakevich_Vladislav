package com.example.dto.vehicle;

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
    private Long id;
    @NotBlank(message = "model cannot be empty")
    private String model;
    @NotNull(message = "max speed cannot be null")
    private Integer maxSpeed;
    @NotNull(message = "must have transportTypeId")
    private Long transportTypeId;
}
