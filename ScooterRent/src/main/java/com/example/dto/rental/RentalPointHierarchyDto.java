package com.example.dto.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class RentalPointHierarchyDto {
    private RentalPointDto mainRentalPoint;
    private List<RentalPointDto> secondaryRentalPoints;
}
