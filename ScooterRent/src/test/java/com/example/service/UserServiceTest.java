package com.example.service;

import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserShortInfoDto;
import com.example.entity.Credential;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.ValidateRegistrationException;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserShortInfoMapper;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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

        ResponseEntity<List<UserShortInfoDto>> response = userService.getUsers(0, 10);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(userShortInfoDto, response.getBody().getFirst());

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

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(rentalService.getAllRentalsByUserId(0, 100, userId)).thenReturn(ResponseEntity.ok(Collections.emptyList()));
        when(userInfoMapper.toUserLongInfoDto(user, Collections.emptyList())).thenReturn(userLongInfoDto);

        ResponseEntity<UserLongInfoDto> response = userService.getUserInfo(userId);

        assertNotNull(response.getBody());
        assertEquals(userLongInfoDto, response.getBody());

        verify(userRepository, times(1)).findById(userId);
        verify(rentalService, times(1)).getAllRentalsByUserId(0, 100, userId);
        verify(userInfoMapper, times(1)).toUserLongInfoDto(user, Collections.emptyList());
    }

    @Test
    void testGetUserInfo_NotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        GetException exception = assertThrows(GetException.class, () -> userService.getUserInfo(userId));
        assertTrue(exception.getMessage().contains(User.class.getSimpleName()));

        verify(userRepository, times(1)).findById(userId);
        verify(rentalService, never()).getAllRentalsByUserId(anyInt(), anyInt(), anyLong());
        verify(userInfoMapper, never()).toUserLongInfoDto(any(), any());
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

        ResponseEntity<String> response = userService.login(loginDto);

        assertNotNull(response.getBody());
        assertEquals("token", response.getBody());

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

        ResponseEntity<String> response = userService.login(loginDto);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Invalid username or password", response.getBody());

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

        ResponseEntity<String> response = userService.register(registerDto);

        assertNotNull(response.getBody());
        assertEquals("Register successful", response.getBody());

        verify(userRepository, times(1)).existsByUsername(registerDto.getUsername());
        verify(roleRepository, times(1)).findByName(Role.USER);
        verify(passwordEncoder, times(1)).encode(registerDto.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_UsernameTaken() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("password");
        registerDto.setConfirmPassword("password");

        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        ValidateRegistrationException exception = assertThrows(ValidateRegistrationException.class, () -> userService.register(registerDto));
        assertEquals("Failed to validate Username is already taken", exception.getMessage());

        verify(userRepository, times(1)).existsByUsername(registerDto.getUsername());
        verify(roleRepository, never()).findByName(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegister_PasswordsDoNotMatch() {
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("password");
        registerDto.setConfirmPassword("differentPassword");

        ValidateRegistrationException exception = assertThrows(ValidateRegistrationException.class, () -> userService.register(registerDto));
        assertEquals("Failed to validate Passwords do not match", exception.getMessage());

        verify(userRepository, never()).existsByUsername(any());
        verify(roleRepository, never()).findByName(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testTopUpBalance_Success() {
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        User user = new User();
        user.setId(userId);
        user.setBalance(new BigDecimal("50.00"));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = userService.topUpBalance(userId, amount);

        assertNotNull(response.getBody());
        assertEquals("Balance successfully updated", response.getBody());
        assertEquals(new BigDecimal("150.00"), user.getBalance());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testTopUpBalance_UserNotFound() {
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.topUpBalance(userId, amount));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
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
        Credential credential = new Credential();
        credential.setEmail("old@example.com");
        credential.setPassword("oldPassword");
        existingUser.setCredential(credential);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<UserRegisterDto> response = userService.updateUser(userId, registerDto);

        assertNotNull(response.getBody());
        assertEquals(registerDto, response.getBody());
        assertEquals("newUsername", existingUser.getUsername());
        assertEquals("new@example.com", existingUser.getCredential().getEmail());
        assertEquals("encodedPassword", existingUser.getCredential().getPassword());

        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).encode(registerDto.getPassword());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UsernameTaken() {
        Long userId = 1L;
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("takenUsername");
        registerDto.setEmail("new@example.com");
        registerDto.setPassword("newPassword");
        registerDto.setConfirmPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        ValidateRegistrationException exception = assertThrows(ValidateRegistrationException.class, () -> userService.updateUser(userId, registerDto));
        assertEquals("Failed to validate Username is already taken", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).existsByUsername(registerDto.getUsername());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateUser_PasswordsDoNotMatch() {
        Long userId = 1L;
        UserRegisterDto registerDto = new UserRegisterDto();
        registerDto.setUsername("newUsername"); // Имя пользователя изменено
        registerDto.setEmail("new@example.com");
        registerDto.setPassword("newPassword");
        registerDto.setConfirmPassword("differentPassword");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setCredential(new Credential());

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername("newUsername")).thenReturn(false);

        ValidateRegistrationException exception = assertThrows(ValidateRegistrationException.class, () -> userService.updateUser(userId, registerDto));
        assertEquals("Failed to validate Passwords do not match", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).existsByUsername("newUsername");
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUser_Success() {
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        ResponseEntity<Void> response = userService.deleteUser(userId);

        assertTrue(response.getStatusCode().is2xxSuccessful());

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
