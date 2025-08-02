package com.budgetPal.controller;

import com.budgetPal.dto.UserModelDto;
import com.budgetPal.dto.UserRegisterDto;
import com.budgetPal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
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

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getUserById (@PathVariable("id") UUID userId) {

        UserModelDto user = userService.getUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<?> getUserByEmail (@PathVariable("email")  String userEmail) {

        UserModelDto user = userService.getUserByEmail(userEmail);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers () {

        List<UserModelDto> users = userService.getAllUser();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/admin/deleteUser/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser (@PathVariable("email") String userEmail) {

        userService.deleteUser(userEmail);

        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }
}