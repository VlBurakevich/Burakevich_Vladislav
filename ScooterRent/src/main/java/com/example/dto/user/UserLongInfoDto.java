package com.example.dto.user;

import com.example.dto.rental.RentalShortInfoDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLongInfoDto {
    private Long id;
    @NotBlank(message = "username cannot be empty")
    private String username;
    @NotNull(message = "balance cannot be null")
    private BigDecimal balance;
    private List<RentalShortInfoDto> rentalShortInfoDtos;
}
