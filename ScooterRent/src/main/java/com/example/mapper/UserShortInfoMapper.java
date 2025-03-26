package com.example.mapper;

import com.example.dto.user.UserShortInfoDto;
import com.example.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserShortInfoMapper {

    UserShortInfoDto entityToDto(User user);
}
