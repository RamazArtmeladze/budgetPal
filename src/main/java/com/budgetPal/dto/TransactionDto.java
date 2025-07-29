package com.budgetPal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDto(
        BigDecimal amount,
        LocalDate transactionDate,
        String description,
        UUID expenseTypeId,
        UUID paymentTypeId,
        UUID budgetId
) {
}
