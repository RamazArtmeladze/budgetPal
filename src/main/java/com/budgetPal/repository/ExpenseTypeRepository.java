package com.budgetPal.repository;

import com.budgetPal.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseTypeRepository  extends JpaRepository<ExpenseType, UUID> {
}
