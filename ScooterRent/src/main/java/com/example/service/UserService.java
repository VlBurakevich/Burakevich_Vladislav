package com.example.service;

import com.example.dto.LoginDto;
import com.example.dto.RegisterDto;
import com.example.dto.UserShortInfoDto;
import com.example.entity.Credential;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.mapper.UserShortInfoMapper;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public ResponseEntity<String> login(LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElse(null);
        if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtService.generateToken(loginDto.getUsername());
        return ResponseEntity.ok(token);
    }

    @Transactional
    public ResponseEntity<String> register(RegisterDto registerDto) {
        validateRegistration(registerDto);
        User newUser = createUser(registerDto);
        userRepository.save(newUser);
        return ResponseEntity.ok("Register successful");
    }

    private void validateRegistration(RegisterDto registerDto) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new RuntimeException(); //TODO
        }
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException(); //TODO
        }
    }

    private User createUser(RegisterDto registerDto) {
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

    public List<UserShortInfoDto> getUsers(int page, int size) {
        Page<User> usersPage = userRepository.findAll(PageRequest.of(page, size));
        return usersPage.getContent()
                .stream()
                .map(UserShortInfoMapper::toDto)
                .collect(Collectors.toList());
    }

    private Role getUserRole() {
        return roleRepository.findByName(Role.USER)
                .orElseThrow(RuntimeException::new);//TODO
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


    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new RuntimeException();//TODO
    }

    public Long getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));//TODO
    }
}
