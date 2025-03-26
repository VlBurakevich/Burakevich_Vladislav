package com.example.controller;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserShortInfoDto;
import com.example.dto.user.UserShortInfoListDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void testGetUsers() {
        UserShortInfoDto userShortInfoDto = new UserShortInfoDto(1L, "user123", new BigDecimal("5000.00"));
        UserShortInfoListDto userListDto = new UserShortInfoListDto(Collections.singletonList(userShortInfoDto));

        when(userService.getUsers(0, 10)).thenReturn(userListDto);

        UserShortInfoListDto response = userController.getUsers(0, 10);

        assertEquals(userListDto, response);
        verify(userService, times(1)).getUsers(0, 10);
    }

    @Test
    void testGetUserInfo() {
        Long userId = 1L;
        List<RentalShortInfoDto> rentals = List.of(
                new RentalShortInfoDto(1L, "user123", new BigDecimal("1500.00"), "Tesla Model S", LocalDateTime.now())
        );
        UserLongInfoDto userLongInfoDto = new UserLongInfoDto(userId, "user123", new BigDecimal("5000.00"), rentals);

        when(userService.getUserInfo(userId)).thenReturn(userLongInfoDto);

        UserLongInfoDto response = userController.getUserInfo(userId);

        assertEquals(userLongInfoDto, response);
        assertEquals(rentals, response.getRentalShortInfoDtos());
        verify(userService, times(1)).getUserInfo(userId);
    }

    @Test
    void testGetCurrentUserInfo() {
        List<RentalShortInfoDto> rentals = List.of(
                new RentalShortInfoDto(1L, "user123", new BigDecimal("1500.00"), "Tesla Model S", LocalDateTime.now())
        );
        UserLongInfoDto userLongInfoDto = new UserLongInfoDto(1L, "user123", new BigDecimal("5000.00"), rentals);

        when(userService.getCurrentUserInfo()).thenReturn(userLongInfoDto);

        UserLongInfoDto response = userController.getCurrentUserInfo();

        assertEquals(userLongInfoDto, response);
        assertEquals(rentals, response.getRentalShortInfoDtos());
        verify(userService, times(1)).getCurrentUserInfo();
    }

    @Test
    void testProcessLogin() {
        UserLoginDto loginDto = new UserLoginDto("user123", "password123");

        when(userService.login(loginDto)).thenReturn("Login successful");

        String response = userController.processLogin(loginDto);

        assertEquals("Login successful", response);
        verify(userService, times(1)).login(loginDto);
    }

    @Test
    void testProcessRegistration() {
        UserRegisterDto registerDto = new UserRegisterDto("user123", "user@example.com", "password123", "password123");

        when(userService.register(registerDto)).thenReturn("Registration successful");

        String response = userController.processRegistration(registerDto);

        assertEquals("Registration successful", response);
        verify(userService, times(1)).register(registerDto);
    }

    @Test
    void testUpdateUserInfo() {
        Long userId = 1L;
        UserRegisterDto registerDto = new UserRegisterDto("updatedUser", "updated@example.com", "newPassword", "newPassword");

        when(userService.updateUser(userId, registerDto)).thenReturn(registerDto);

        UserRegisterDto response = userController.updateUserInfo(userId, registerDto);

        assertEquals(registerDto, response);
        verify(userService, times(1)).updateUser(userId, registerDto);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        userService.deleteUser(userId);
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testTopUpBalance() {
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("1000.00");

        when(userService.topUpBalance(userId, amount)).thenReturn("Balance topped up");

        String response = userController.topUpBalance(userId, amount);

        assertEquals("Balance topped up", response);
        verify(userService, times(1)).topUpBalance(userId, amount);
    }
}
