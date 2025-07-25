package com.budgetPal.dto;

import jakarta.validation.constraints.Email;

public record UserRegisterDto (

    String name,

    String lastName,

    @Email(message = "email must be valid")
    String email,

    String password,

    String passwordConfirmation
) {

    public UserRegisterDto UserWithHashedPassword(String password) {
        return new UserRegisterDto(this.name, this.lastName, this.email, password, this.passwordConfirmation);
    }


}
