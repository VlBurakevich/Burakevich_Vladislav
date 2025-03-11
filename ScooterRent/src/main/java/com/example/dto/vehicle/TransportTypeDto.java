package com.example.dto.vehicle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportTypeDto {

    private Long id;
    @NotBlank(message = "typeName cannot be empty")
    private String typeName;
    @NotNull(message = "basePrice cannot be empty")
    private BigDecimal basePrice;
}