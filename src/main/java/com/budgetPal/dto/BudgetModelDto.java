package com.budgetPal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BudgetModelDto(
        UUID budgetId,
        String name,
        BigDecimal totalAmount,
        LocalDate startDate,
        LocalDate endDate,
        List<UUID> expenseTypeIds,
        LocalDateTime createdAt
) {
}
