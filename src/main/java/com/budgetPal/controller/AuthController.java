package com.budgetPal.controller;

import com.budgetPal.dto.AuthRequest;
import com.budgetPal.exception.DisabledUserException;
import com.budgetPal.exception.InvalidCredentialsException;
import com.budgetPal.exception.UserNotFoundException;
import com.budgetPal.model.User;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.utility.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.budgetPal.utility.MessageConstants.DEACTIVATED_USER_MESSAGE;
import static com.budgetPal.utility.MessageConstants.INVALID_CREDENTIALS_MESSAGE;
import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_EMAIL_MESSAGE;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public String generateToken(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            User user = userRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE));

            if (!user.isActive()) {
                throw new DisabledUserException(DEACTIVATED_USER_MESSAGE);
            }

            List<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            UUID userId = user.getUserId();

            return jwtUtil.generateToken(userId, authRequest.getEmail(), roles);

        } catch (AuthenticationException ex) {

            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }
    }
}
