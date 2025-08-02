package com.budgetPal.repository;

import com.budgetPal.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, UUID> {

    @Query(value = "SELECT * FROM payment_types WHERE name = :name", nativeQuery = true)
    Optional<PaymentType> findByName(@Param("name") String name);
}
