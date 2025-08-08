package com.budgetPal.service.impl;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;
import com.budgetPal.exception.UserNotFoundException;
import com.budgetPal.mapper.UserModelMapper;
import com.budgetPal.model.Role;
import com.budgetPal.model.User;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.utility.CurrentUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserModelMapper userModelMapper;

    private User user;

    private UserModelDto userModelDto;

    private UserRegisterDto userRegisterDto;

    @BeforeEach
    void setup() {

        user = new User();
        user.setName("name");
        user.setLastName("lastName");
        user.setEmail("test@test.com");
        user.setActive(true);
        user.setUserId(UUID.randomUUID());
        user.setPassword("pa");

        userRegisterDto = new UserRegisterDto("name", "lastName", "test@test.com", "pa", "pa");

        userModelDto = new UserModelDto(UUID.randomUUID(), "name", "lastName", "test@test.com", Role.USER);

    }

    @Test
    void userRegistration() {

        when(passwordEncoder.encode("pa")).thenReturn("hashedPassword");
        UserRegisterDto hashedUser = userRegisterDto.userWithHashedPassword("hashedPassword");

        when(userModelMapper.toEntity(hashedUser)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userModelMapper.toDto(user)).thenReturn(userModelDto);

        UserModelDto result = userService.userRegistration(userRegisterDto);

        assertNotNull(result);
        assertEquals("name", result.name());
        assertEquals("lastName", result.lastName());
        assertEquals("test@test.com", result.email());
    }

    @Test
    void updateUserNameByEmail() {

        userRegisterDto = new UserRegisterDto("newName", "lastName", "test@test.com", null, null);

        when(userRepository.findByEmail(userRegisterDto.email())).thenReturn(Optional.of(user));

        userModelDto = new UserModelDto(user.getUserId(), "newName", "lastName", "test@test.com", Role.USER);

        when(userRepository.save(user)).thenReturn(user);
        when(userModelMapper.toDto(user)).thenReturn(userModelDto);


        UserModelDto result = userService.updateUserNameByEmail(userRegisterDto);

        assertNotNull(result);
        assertEquals("newName", result.name());
        assertEquals("lastName", result.lastName());
    }


    @Test
    void getUserById() {

        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userModelMapper.toDto(user)).thenReturn(userModelDto);

        UserModelDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("name", result.name());
    }

    @Test
    void getUserById_notFound() {

        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getUserByEmail() {

        String userEmail = "test@user.com";

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(userModelMapper.toDto(user)).thenReturn(userModelDto);

        UserModelDto result = userService.getUserByEmail(userEmail);

        assertEquals("name", result.name());
    }

    @Test
    void getAllUser() {

        List<User> userList = List.of(user);
        List<UserModelDto> dtoList = List.of(userModelDto);

        when(userRepository.findAll()).thenReturn(userList);
        when(userModelMapper.toDto(userList)).thenReturn(dtoList);

        List<UserModelDto> result = userService.getAllUser();

        assertEquals(1, result.size());
    }
}