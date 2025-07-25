package com.budgetPal.controller;


import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;
import com.budgetPal.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/app/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        if (!Objects.equals(userRegisterDto.password(), userRegisterDto.passwordConfirmation())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password confirmation doesn't match");
        }

        UserModelDto userModelDto = userService.userRegistration(userRegisterDto);

        return new ResponseEntity<>(userModelDto, HttpStatus.CREATED);
    }
}
