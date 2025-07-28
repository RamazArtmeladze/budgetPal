package com.budgetPal.dto;

import com.budgetPal.model.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BudgetDto(
        String name,
        BigDecimal totalAmount,
        LocalDate startDate,
        LocalDate endDate,
        UUID expenseTypeId
) {
}
