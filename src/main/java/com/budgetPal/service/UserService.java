package com.budgetPal.service;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserModelDto userRegistration (UserRegisterDto userRegisterDto);

    UserModelDto getUserById (UUID userId);

    UserModelDto getUserByEmail (String userEmail);

    List<UserModelDto> getAllUser();

    void deleteUser(String email);
}
