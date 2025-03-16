package com.example.controller;

import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserShortInfoDto;
import com.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
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
        UserShortInfoDto userShortInfoDto = new UserShortInfoDto();
        userShortInfoDto.setId(1L);
        userShortInfoDto.setUsername("user123");
        userShortInfoDto.setBalance(new BigDecimal("5000.00"));

        List<UserShortInfoDto> userList = Collections.singletonList(userShortInfoDto);

        when(userService.getUsers(0, 10)).thenReturn(ResponseEntity.ok(userList));

        ResponseEntity<List<UserShortInfoDto>> response = userController.getUsers(0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userList, response.getBody());
        verify(userService, times(1)).getUsers(0, 10);
    }

    @Test
    void testGetUserInfo() {
        Long userId = 1L;
        UserLongInfoDto userLongInfoDto = new UserLongInfoDto();
        userLongInfoDto.setId(userId);
        userLongInfoDto.setUsername("user123");
        userLongInfoDto.setBalance(new BigDecimal("5000.00"));

        when(userService.getUserInfo(userId)).thenReturn(ResponseEntity.ok(userLongInfoDto));

        ResponseEntity<UserLongInfoDto> response = userController.getUserInfo(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userLongInfoDto, response.getBody());
        verify(userService, times(1)).getUserInfo(userId);
    }

    @Test
    void testGetCurrentUserInfo() {
        UserLongInfoDto userLongInfoDto = new UserLongInfoDto();
        userLongInfoDto.setId(1L);
        userLongInfoDto.setUsername("user123");
        userLongInfoDto.setBalance(new BigDecimal("5000.00"));

        when(userService.getCurrentUserInfo()).thenReturn(ResponseEntity.ok(userLongInfoDto));

        ResponseEntity<UserLongInfoDto> response = userController.getCurrentUserInfo();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userLongInfoDto, response.getBody());
        verify(userService, times(1)).getCurrentUserInfo();
    }

    @Test
    void testProcessLogin() {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("user123");
        loginDto.setPassword("password123");

        when(userService.login(loginDto)).thenReturn(ResponseEntity.ok("Login successful"));

        ResponseEntity<String> response = userController.processLogin(loginDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Login successful", response.getBody());
        verify(userService, times(1)).login(loginDto);
    }

    @Test
    void testProcessRegistration() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("user123");
        registerDto.setEmail("user@example.com");
        registerDto.setPassword("password123");
        registerDto.setConfirmPassword("password123");

        when(userService.register(registerDto)).thenReturn(ResponseEntity.ok("Registration successful"));

        ResponseEntity<String> response = userController.processRegistration(registerDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Registration successful", response.getBody());
        verify(userService, times(1)).register(registerDto);
    }

    @Test
    void testUpdateUserInfo() {
        Long userId = 1L;
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("updatedUser");
        registerDto.setEmail("updated@example.com");
        registerDto.setPassword("newPassword");
        registerDto.setConfirmPassword("newPassword");

        when(userService.updateUser(userId, registerDto)).thenReturn(ResponseEntity.ok(registerDto));

        ResponseEntity<UserRegisterDto> response = userController.updateUserInfo(userId, registerDto);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(registerDto, response.getBody());
        verify(userService, times(1)).updateUser(userId, registerDto);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        when(userService.deleteUser(userId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(204, response.getStatusCode().value());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testTopUpBalance() {
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("1000.00");

        when(userService.topUpBalance(userId, amount)).thenReturn(ResponseEntity.ok("Balance topped up"));

        ResponseEntity<String> response = userController.topUpBalance(userId, amount);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Balance topped up", response.getBody());
        verify(userService, times(1)).topUpBalance(userId, amount);
    }
}
