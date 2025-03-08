package com.example.mapper;

import com.example.dto.rental.RentalPointInfoDto;
import com.example.entity.RentalPoint;
import com.example.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalPointInfoMapper {
    RentalPointInfoMapper INSTANCE = Mappers.getMapper(RentalPointInfoMapper.class);

    RentalPointInfoDto toRentalPointInfoDto(RentalPoint rentalPoint, List<Vehicle> vehicles);
}
