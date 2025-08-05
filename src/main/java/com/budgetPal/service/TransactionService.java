package com.budgetPal.service;

import com.budgetPal.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDto transactionRegister (TransactionDto transactionDto);

    TransactionDto updateTransaction(TransactionDto transactionDto);

    void deleteTransaction(UUID id);

    Page<TransactionDto> getTransactionsByUserId(Pageable pageable, UUID userId);

    TransactionDto getTransactionById(UUID id);

    Page<TransactionDto> getAll(Pageable pageable);

    List<TransactionDto> getTransactionsByBudget(UUID budgetId);

    List<TransactionDto> getTransactionsByExpenseType(String name);

    List<TransactionDto> getTransactionsByPaymentType(String name);

    BigDecimal getSumOfTransactionsAmountByBudget(UUID budgetId);

    BigDecimal getSpentPercentageByBudget (UUID budgetId);

    BigDecimal getSumOfTransactionsAmountByExpenseType(UUID expenseTypeId);

    BigDecimal getSumOfTransactionsAmountByPaymentType(UUID paymentTypeId);

    List<TransactionDto> getTransactionsBetweenDates(LocalDate from, LocalDate to);

    BigDecimal getSumBetweenDates(LocalDate from, LocalDate to);
}
