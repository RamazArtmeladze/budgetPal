package com.budgetPal.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record UserRegisterDto (
    String name,
    String lastName,
    @Email(message = "email must be valid")
    String email,
    @Nullable
    String password,
    @Nullable
    String passwordConfirmation
) {

    public UserRegisterDto userWithHashedPassword(String password) {
        return new UserRegisterDto(this.name, this.lastName, this.email, password, this.passwordConfirmation);
    }
}
