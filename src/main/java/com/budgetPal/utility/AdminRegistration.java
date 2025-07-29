package com.budgetPal.utility;

import com.budgetPal.model.Role;
import com.budgetPal.model.User;
import com.budgetPal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminRegistration implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@budgetpal.com").isEmpty()) {
            User admin = User.builder()
                    .name("Admin")
                    .lastName("User")
                    .email("admin@budgetpal.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .build();

            userRepository.save(admin);
            System.out.println("Admin user created successfully");
        }else {
            System.out.println("Admin user already exist");
        }
    }
}

