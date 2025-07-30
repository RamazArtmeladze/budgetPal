package com.budgetPal.service.impl;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;
import com.budgetPal.mapper.UserModelMapper;
import com.budgetPal.model.User;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserModelMapper userModelMapper;

    @Override
    public UserModelDto userRegistration(UserRegisterDto userRegisterDto) {
        String hashedPassword = passwordEncoder.encode(userRegisterDto.password());

        userRegisterDto = userRegisterDto.UserWithHashedPassword(hashedPassword);

        User savedUser = userRepository.save(userModelMapper.toEntity(userRegisterDto));

        return userModelMapper.toDto(savedUser);
    }

    @Override
    public UserModelDto getUserById(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userModelMapper.toDto(user);
    }

    @Override
    public UserModelDto getUserByEmail(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userModelMapper.toDto(user);
    }

    @Override
    public List<UserModelDto> getAllUser() {

        List<User> users = userRepository.findAll();

        return userModelMapper.toDto(users);
    }

    @Override
    public void deleteUser(String email) {

        userRepository.delete(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found")));

    }
}