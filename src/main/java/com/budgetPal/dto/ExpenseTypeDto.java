package com.budgetPal.dto;

import java.util.UUID;

public record ExpenseTypeDto(
        UUID expenseTypeId,
        String name
) {
}
