package org.senla.service;


import org.senla.dao.CredentialsDao;
import org.senla.dao.UserDao;
import org.senla.di.annotations.Autowired;
import org.senla.di.annotations.Component;
import org.senla.dto.LoginDto;
import org.senla.dto.RegisterDto;
import org.senla.entity.Credential;
import org.senla.entity.User;
import org.senla.exceptions.entityExceptions.CredentialsNotFoundException;
import org.senla.exceptions.entityExceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorizationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CredentialsDao credentialsDao;

    public List<String> validateLogin(LoginDto loginDto) {
        List<String> errors = new ArrayList<>();

        if (loginDto.getUsername() == null || loginDto.getPassword() == null) {
            errors.add("Username must not be empty.");
        }
        if (loginDto.getPassword() == null || loginDto.getPassword().isEmpty()) {
            errors.add("Password must not be empty.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        try {
            User user = userDao.getByUsername(loginDto.getUsername());
            Credential credential = credentialsDao.getByUserId(user.getId());
            if (!credential.getPassword().equals(loginDto.getPassword())) {
                errors.add("Invalid username or password.");
            }
        } catch (UserNotFoundException | CredentialsNotFoundException e) {
            errors.add("Invalid username or password.");
        }

        return errors;
    }

    public void register(RegisterDto registerDto) {
        try {
            User existingUser = userDao.getByUsername(registerDto.getUsername());
            if (existingUser != null) {
                throw new IllegalArgumentException("Username is already taken");
            }
        } catch (UserNotFoundException _) {
        }

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());

        Credential credential = new Credential();
        credential.setPassword(registerDto.getPassword());
        credential.setEmail(registerDto.getEmail());

        newUser.setCredential(credential);

        userDao.createUser(newUser);
    }

    public boolean isUsernameAvailable(String username) {
        try {
            userDao.getByUsername(username);
            return false;
        } catch (UserNotFoundException _) {
            return true;
        }
    }

    public List<String> validateRegistration(String username, String email, String password, String confirmPassword) {
        List<String> errors = new ArrayList<>();

        checkEmptyField(username, "Username must not be empty.", errors);
        checkEmptyField(email, "Email must not be empty.", errors);
        checkEmptyField(password, "Password must not be empty.", errors);
        checkEmptyField(confirmPassword, "Confirm password must not be empty.", errors);

        if (password != null && !password.equals(confirmPassword)) {
            errors.add("Passwords do not match.");
        }

        if (username != null && !isUsernameAvailable(username)) {
            errors.add("Username is already taken.");
        }

        return errors;
    }

    private void checkEmptyField(String field, String errorMessage, List<String> errors) {
        if (field == null || field.isEmpty()) {
            errors.add(errorMessage);
        }
    }


}
