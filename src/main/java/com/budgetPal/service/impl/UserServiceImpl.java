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
}
