package com.example.dto;

import com.example.enums.DiscountTypeEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {

    private Long id;
    @NotBlank(message = "name cannot be empty")
    private String name;
    @NotBlank(message = "discountType cannot be empty")
    private DiscountTypeEnum discountType;
    @NotBlank(message = "discountAmount cannot be empty")
    private BigDecimal discountAmount;
}