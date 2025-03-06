package com.example.dto;

import com.example.enums.TarifTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarifDto {

    private Long id;
    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "tarifType cannot be empty")
    private TarifTypeEnum tarifType;
    @NotBlank(message = "basePrice cannot be empty")
    private BigDecimal basePrice;
    @NotBlank(message = "unitTime cannot be empty")
    private Integer unitTime;
    @NotBlank(message = "transportTypeId cannot be empty")
    private Long transportTypeId;
}