package com.budgetPal.repository;

import com.budgetPal.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId", nativeQuery = true)
    Page<Transaction> findByUserId(UUID userId, Pageable pageable);

    List<Transaction> findByBudgetId(UUID budgetId);

    List<Transaction> findByExpenseTypeId(UUID expenseTypeId);

    List<Transaction> findByPaymentTypeId(UUID paymentTypeId);

    List<Transaction> findByTransactionDateBetween(LocalDate from, LocalDate to);
}
