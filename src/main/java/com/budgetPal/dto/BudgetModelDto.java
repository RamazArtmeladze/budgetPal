package com.budgetPal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record BudgetModelDto(
        UUID budgetId,
        String name,
        BigDecimal totalAmount,
        LocalDate startDate,
        LocalDate endDate,
        UUID expenseTypeId,
        LocalDateTime createdAt
) {
}
