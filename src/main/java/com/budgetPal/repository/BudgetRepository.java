package com.budgetPal.repository;

import com.budgetPal.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    @Query(value = "SELECT * FROM budgets WHERE user_id = :userId", nativeQuery = true)
    List<Budget> findByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM budgets WHERE expense_type_id = :expenseTypeId", nativeQuery = true)
    List<Budget> findByExpenseType(@Param("expenseTypeId") UUID expenseTypeId);
}
