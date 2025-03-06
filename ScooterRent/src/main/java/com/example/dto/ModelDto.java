package com.example.dto;

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
    private String model;
    private Integer maxSpeed;
    private Long transportTypeId;
}
