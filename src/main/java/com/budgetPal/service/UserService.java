package com.budgetPal.service;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;

public interface UserService {

    UserModelDto userRegistration (UserRegisterDto userRegisterDto);
}
