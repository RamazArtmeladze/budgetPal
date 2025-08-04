package com.budgetPal.repository;

import com.budgetPal.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId", nativeQuery = true)
    Page<Transaction> findByUserId(UUID userId, Pageable pageable);
}
