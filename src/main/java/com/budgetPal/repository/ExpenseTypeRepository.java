package com.budgetPal.repository;

import com.budgetPal.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ExpenseTypeRepository  extends JpaRepository<ExpenseType, UUID> {

    @Query(value = "SELECT * FROM expense_types WHERE name = :name", nativeQuery = true)
    Optional<ExpenseType> findByName(@Param("name") String name);
}
