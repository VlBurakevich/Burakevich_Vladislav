package com.example.mapper;

import com.example.dto.user.UserShortInfoDto;
import org.mapstruct.Mapper;
import com.example.entity.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserShortInfoMapper {

    UserShortInfoDto entityToDto(User user);

    @Mapping(target = "credential", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "rentals", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    User dtoToEntity(UserShortInfoDto userShortInfoDto);
}
