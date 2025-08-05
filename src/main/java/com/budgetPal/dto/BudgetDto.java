package com.budgetPal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BudgetDto(
        String name,
        BigDecimal totalAmount,
        LocalDate startDate,
        LocalDate endDate,
        List<UUID> expenseTypeIds
) {
}
