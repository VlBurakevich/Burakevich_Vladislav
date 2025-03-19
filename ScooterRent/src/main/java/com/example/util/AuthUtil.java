package com.example.util;

import com.example.entity.User;
import com.example.exceptions.AuthenticationException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthUtil {
    public static User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new AuthenticationException();
    }

    public static String getAuthenticatedUsername() {
        return getAuthenticatedUser().getUsername();
    }

    public static Long getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }
}
