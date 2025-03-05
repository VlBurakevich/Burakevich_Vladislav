package com.example.mapper;

import com.example.dto.UserShortInfoDto;
import org.springframework.beans.BeanUtils;
import com.example.entity.User;

public class UserShortInfoMapper {
    public static UserShortInfoDto toDto(User user) {
        UserShortInfoDto dto = new UserShortInfoDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
