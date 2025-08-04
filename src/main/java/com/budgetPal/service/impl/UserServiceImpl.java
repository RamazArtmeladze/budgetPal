package com.budgetPal.service.impl;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;
import com.budgetPal.exception.UserNotFoundException;
import com.budgetPal.mapper.UserModelMapper;
import com.budgetPal.model.User;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.service.UserService;
import com.budgetPal.utility.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_EMAIL_MESSAGE;
import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_ID_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserModelMapper userModelMapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public UserModelDto userRegistration(UserRegisterDto userRegisterDto) {
        String hashedPassword = passwordEncoder.encode(userRegisterDto.password());

        userRegisterDto = userRegisterDto.UserWithHashedPassword(hashedPassword);

        User savedUser = userRepository.save(userModelMapper.toEntity(userRegisterDto));

        return userModelMapper.toDto(savedUser);
    }

    @Override
    public UserModelDto updateUserNameByEmail(UserRegisterDto userRegisterDto) {

        User user = userRepository.findByEmail(userRegisterDto.email())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE));

        user.setName(userRegisterDto.name());

        User updatedUser =  userRepository.save(user);

        return userModelMapper.toDto(updatedUser);
    }

    @Override
    public UserModelDto updateUserLastNameByEmail(UserRegisterDto userRegisterDto) {
        User user = userRepository.findByEmail(userRegisterDto.email())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE));

        user.setLastName(userRegisterDto.lastName());

        User updatedUser =  userRepository.save(user);

        return userModelMapper.toDto(updatedUser);
    }

    @Override
    public UserModelDto getUserById(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE));

        return userModelMapper.toDto(user);
    }

    @Override
    public UserModelDto getUserByEmail(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE));

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
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE)));
    }

    @Override
    public UUID getCurrentSignedUserId() {
        return currentUserProvider.getCurrentUserId();
    }

    @Override
    public String getCurrentSignedUserEmail() {
        return currentUserProvider.getCurrentUserEmail();
    }
}