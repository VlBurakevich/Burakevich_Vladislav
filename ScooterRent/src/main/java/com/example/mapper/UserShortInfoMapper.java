package com.example.mapper;

import com.example.dto.UserShortInfoDto;
import org.mapstruct.Mapper;
import com.example.entity.User;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserShortInfoMapper {
    UserShortInfoMapper INSTANCE = Mappers.getMapper(UserShortInfoMapper.class);

    UserShortInfoDto entityToDto(User user);

    User dtoToEntity(UserShortInfoDto userShortInfoDto);
}
