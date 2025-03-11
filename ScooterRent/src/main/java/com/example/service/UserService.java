package com.example.service;

import com.example.dto.rental.RentalShortInfoDto;
import com.example.dto.user.UserLoginDto;
import com.example.dto.user.UserLongInfoDto;
import com.example.dto.user.UserRegisterDto;
import com.example.dto.user.UserShortInfoDto;
import com.example.entity.Credential;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.exceptions.ValidateRegistrationException;
import com.example.mapper.UserInfoMapper;
import com.example.mapper.UserShortInfoMapper;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final RentalService rentalService;
    private final UserShortInfoMapper userShortInfoMapper;
    private final UserInfoMapper userInfoMapper;

    public ResponseEntity<String> login(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElse(null);
        if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtService.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(token);
    }

    @Transactional
    public ResponseEntity<String> register(UserRegisterDto registerDto) {
        validateRegistration(registerDto);
        User newUser = createUser(registerDto);
        userRepository.save(newUser);
        return ResponseEntity.ok("Register successful");
    }

    private void validateRegistration(UserRegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new ValidateRegistrationException("Passwords do not match");
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new ValidateRegistrationException("Username is already taken");
        }
    }

    private User createUser(UserRegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setBalance(BigDecimal.ZERO);
        user.addRole(getUserRole());

        Credential credential = new Credential();
        credential.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        credential.setEmail(registerDto.getEmail());
        user.setCredential(credential);

        return user;
    }

    public ResponseEntity<List<UserShortInfoDto>> getUsers(int page, int size) {
        Page<User> usersPage = userRepository.findAll(PageRequest.of(page, size));
        List<UserShortInfoDto> users = usersPage.getContent()
                .stream()
                .map(userShortInfoMapper::entityToDto)
                .toList();
        return ResponseEntity.ok(users);
    }

    private Role getUserRole() {
        return roleRepository.findByName(Role.USER)
                .orElseThrow(() -> new GetException(Role.class.getSimpleName()));
    }

    @Transactional
    public ResponseEntity<String> topUpBalance(Long userId, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);

        BigDecimal balance = user.getBalance().add(amount);
        user.setBalance(balance);

        userRepository.save(user);
        return ResponseEntity.ok().body("Balance successfully updated");
    }

    public ResponseEntity<UserLongInfoDto> getUserInfo(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GetException(User.class.getSimpleName()));

        List<RentalShortInfoDto> rentals = rentalService.getAllRentalsByUserId(0, 100, id).getBody();

        return ResponseEntity.ok(userInfoMapper.toUserLongInfoDto(user, rentals));
    }

    @Transactional
    public ResponseEntity<UserRegisterDto> updateUser(Long id, UserRegisterDto registerDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UpdateException(User.class.getSimpleName()));

        if (!user.getUsername().equals(registerDto.getUsername()) && userRepository.existsByUsername(registerDto.getUsername())) {
                throw new ValidateRegistrationException("Username is already taken");
            }


        user.setUsername(registerDto.getUsername());
        user.getCredential().setEmail(registerDto.getEmail());

        if (registerDto.getPassword() != null && !registerDto.getPassword().isBlank()) {
            if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
                throw new ValidateRegistrationException("Passwords do not match");
            }
            user.getCredential().setPassword(passwordEncoder.encode(registerDto.getPassword()));
        }

        userRepository.save(user);

        return ResponseEntity.ok(registerDto);
    }

    public ResponseEntity<Void> deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new DeleteException(User.class.getSimpleName());
        }
        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
