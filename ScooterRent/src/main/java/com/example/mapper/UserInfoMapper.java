package com.example.mapper;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    UserLongInfoDto toUserLongInfoDto(User user, List<RentalShortInfoDto> rentals);
}
