package com.budgetPal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public record TransactionFilter(
        LocalDate fromDate,
        LocalDate toDate,
        BigDecimal minAmount,
        BigDecimal maxAmount,
        UUID expenseTypeId,
        UUID paymentTypeId,
        Pageable pageable
) {
}
