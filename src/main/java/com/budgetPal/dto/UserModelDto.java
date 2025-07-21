package com.budgetPal.dto;

import java.util.UUID;

public record UserModelDto(
        UUID userId,

        String name,

        String lastName,

        String email,

        String password
) {
}
