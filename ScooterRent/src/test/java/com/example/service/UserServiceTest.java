package com.example.service;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserShortInfoDto;
import com.example.dto.user.UserShortInfoListDto;
import com.example.entity.Credential;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.exceptions.DeleteException;
import com.example.exceptions.ValidateException;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserShortInfoMapper;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtService;
import com.example.util.AuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private RentalService rentalService;

    @Mock
    private UserShortInfoMapper userShortInfoMapper;

    @Mock
    private UserInfoMapper userInfoMapper;

    @Test
    void testGetUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        UserShortInfoDto userShortInfoDto = new UserShortInfoDto();
        userShortInfoDto.setId(1L);
        userShortInfoDto.setUsername("testUser");

        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(userPage);
        when(userShortInfoMapper.entityToDto(user)).thenReturn(userShortInfoDto);

        UserShortInfoListDto result = userService.getUsers(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getUserShortInfoDtoList().size());
        assertEquals(userShortInfoDto, result.getUserShortInfoDtoList().getFirst());

        verify(userRepository, times(1)).findAll(any(PageRequest.class));
        verify(userShortInfoMapper, times(1)).entityToDto(user);
    }

    @Test
    void testGetUserInfo() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testUser");

        UserLongInfoDto userLongInfoDto = new UserLongInfoDto();
        userLongInfoDto.setId(userId);
        userLongInfoDto.setUsername("testUser");

        List<RentalShortInfoDto> emptyRentals = Collections.emptyList();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(rentalService.getAllRentalsByUserId(0, 100, userId)).thenReturn(emptyRentals);
        when(userInfoMapper.toUserLongInfoDto(user, emptyRentals)).thenReturn(userLongInfoDto);

        UserLongInfoDto result = userService.getUserInfo(userId);

        assertNotNull(result);
        assertEquals(userLongInfoDto, result);

        verify(userRepository, times(1)).findById(userId);
        verify(rentalService, times(1)).getAllRentalsByUserId(0, 100, userId);
        verify(userInfoMapper, times(1)).toUserLongInfoDto(user, emptyRentals);
    }

    @Test
    void testGetCurrentUserInfo() {
        try (MockedStatic<AuthUtil> mockedAuthUtil = Mockito.mockStatic(AuthUtil.class)) {
            Long userId = 1L;
            User user = new User();
            user.setId(userId);
            user.setUsername("testUser");

            UserLongInfoDto userLongInfoDto = new UserLongInfoDto();
            userLongInfoDto.setId(userId);
            userLongInfoDto.setUsername("testUser");

            List<RentalShortInfoDto> emptyRentals = Collections.emptyList();

            mockedAuthUtil.when(AuthUtil::getAuthenticatedUserId).thenReturn(userId);
            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(rentalService.getAllRentalsByUserId(0, 100, userId)).thenReturn(emptyRentals);
            when(userInfoMapper.toUserLongInfoDto(user, emptyRentals)).thenReturn(userLongInfoDto);

            UserLongInfoDto result = userService.getCurrentUserInfo();

            assertNotNull(result);
            assertEquals(userLongInfoDto, result);

            verify(userRepository, times(1)).findById(userId);
            verify(rentalService, times(1)).getAllRentalsByUserId(0, 100, userId);
            verify(userInfoMapper, times(1)).toUserLongInfoDto(user, emptyRentals);
        }
    }

    @Test
    void testLogin_Success() {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("testUser");
        loginDto.setPassword("password");

        User user = new User();
        user.setUsername("testUser");
        user.setCredential(new Credential());
        user.getCredential().setPassword("encodedPassword");

        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginDto.getPassword(), user.getCredential().getPassword())).thenReturn(true);
        when(jwtService.generateToken(loginDto.getUsername())).thenReturn("token");

        String result = userService.login(loginDto);

        assertEquals("token", result);

        verify(userRepository, times(1)).findByUsername(loginDto.getUsername());
        verify(passwordEncoder, times(1)).matches(loginDto.getPassword(), user.getCredential().getPassword());
        verify(jwtService, times(1)).generateToken(loginDto.getUsername());
    }

    @Test
    void testLogin_InvalidCredentials() {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("testUser");
        loginDto.setPassword("password");

        when(userRepository.findByUsername(loginDto.getUsername())).thenReturn(Optional.empty());

        ValidateException exception = assertThrows(ValidateException.class, () -> userService.login(loginDto));
        assertTrue(exception.getMessage().contains(User.class.getSimpleName()));

        verify(userRepository, times(1)).findByUsername(loginDto.getUsername());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void testRegister_Success() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("password");
        registerDto.setConfirmPassword("password");

        Role role = new Role();
        role.setName(Role.USER);
        role.setUsers(new ArrayList<>());

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(false);
        when(roleRepository.findByName(Role.USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        String result = userService.register(registerDto);

        assertEquals("Register successful", result);

        verify(userRepository, times(1)).existsByUsername(registerDto.getUsername());
        verify(roleRepository, times(1)).findByName(Role.USER);
        verify(passwordEncoder, times(1)).encode(registerDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        Long userId = 1L;
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("newUsername");
        registerDto.setEmail("new@example.com");
        registerDto.setPassword("newPassword");
        registerDto.setConfirmPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setCredential(new Credential());
        existingUser.getCredential().setEmail("old@example.com");
        existingUser.getCredential().setPassword("oldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");

        UserRegisterDto result = userService.updateUser(userId, registerDto);

        assertNotNull(result);
        assertEquals(registerDto, result);
        assertEquals("newUsername", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getCredential().getEmail());
        assertEquals("encodedPassword", existingUser.getCredential().getPassword());

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).encode(registerDto.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testTopUpBalance_Success() {
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        User user = new User();
        user.setId(userId);
        user.setBalance(new BigDecimal("50.00"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        String result = userService.topUpBalance(userId, amount);

        assertEquals("Balance successfully updated", result);
        assertEquals(new BigDecimal("150.00"), user.getBalance());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(userId));

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUser_NotFound() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        DeleteException exception = assertThrows(DeleteException.class, () -> userService.deleteUser(userId));
        assertTrue(exception.getMessage().contains(User.class.getSimpleName()));

        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(any());
    }
}
