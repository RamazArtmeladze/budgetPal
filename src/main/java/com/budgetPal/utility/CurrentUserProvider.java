package com.budgetPal.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserProvider {

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            throw new IllegalStateException("Authenticated principal is not CustomUserDetails");
        }
    }

    public UUID getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getUserId();
        } else {
            throw new IllegalStateException("Authenticated principal is not CustomUserDetails");
        }
    }
}
