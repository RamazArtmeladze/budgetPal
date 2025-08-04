package com.budgetPal.dto;

import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionDto(
        @Nullable
        UUID transactionId,
        BigDecimal amount,
        LocalDate transactionDate,
        String description,
        UUID expenseTypeId,
        UUID paymentTypeId,
        UUID budgetId
) {
}
