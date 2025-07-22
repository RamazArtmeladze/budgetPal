package com.budgetPal.dto;

import com.budgetPal.model.Role;

import java.util.UUID;

public record UserModelDto(
        UUID userId,

        String name,

        String lastName,

        String email,

        Role role
) {
}
