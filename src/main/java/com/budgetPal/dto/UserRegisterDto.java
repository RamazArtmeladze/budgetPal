package com.budgetPal.dto;

public record UserRegisterDto (

    String name,

    String lastName,

    String email,

    String password,

    String passwordConfirmation
) {

    public UserRegisterDto UserWithHashedPassword(String password) {
        return new UserRegisterDto(this.name, this.lastName, this.email, password, this.passwordConfirmation);
    }


}
