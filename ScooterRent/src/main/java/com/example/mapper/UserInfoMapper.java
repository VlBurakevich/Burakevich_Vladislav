package com.example.mapper;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    @Mapping(target = "rentalShortInfoDtos", ignore = true)
    UserLongInfoDto toUserLongInfoDto(User user);

    default UserLongInfoDto toUserLongInfoDto(User user, List<RentalShortInfoDto> rentals) {
        UserLongInfoDto dto = toUserLongInfoDto(user);
        dto.setRentalShortInfoDtos(rentals);
        return dto;
    }
}
