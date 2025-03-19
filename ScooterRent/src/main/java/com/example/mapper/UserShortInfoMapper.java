package com.example.mapper;

import com.example.dto.user.UserShortInfoDto;
import org.mapstruct.Mapper;
import com.example.entity.User;

@Mapper(componentModel = "spring")
public interface UserShortInfoMapper {

    UserShortInfoDto entityToDto(User user);
}
