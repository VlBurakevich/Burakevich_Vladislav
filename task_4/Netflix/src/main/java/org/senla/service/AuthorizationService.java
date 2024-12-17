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

@Component
public class AuthorizationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CredentialsDao credentialsDao;

    public boolean login(LoginDto loginDto) {
        try {
            User user = userDao.getByUsername(loginDto.getUsername());
            Credential credential = credentialsDao.getByUserId(user.getId());

            return credential.getPassword().equals(loginDto.getPassword());
        } catch (UserNotFoundException | CredentialsNotFoundException e) {
            return false;
        }
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
}
