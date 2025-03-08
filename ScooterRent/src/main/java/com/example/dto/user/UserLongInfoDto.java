package com.example.dto.user;

import com.example.dto.rental.RentalShortInfoDto;
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
    private String username;
    private BigDecimal balance;
    private List<RentalShortInfoDto> rentalShortInfoDtos;
}
