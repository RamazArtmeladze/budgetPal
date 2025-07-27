package com.budgetPal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "payment_types")
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_type_id", nullable = false, unique = true)
    private UUID paymentTypeId;

    @Column(name = "name", nullable = false, unique = true)
    private String name; // byCash, byCard, byBitcoin

    @OneToMany(mappedBy = "paymentType", fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
