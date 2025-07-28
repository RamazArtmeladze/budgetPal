package com.budgetPal.dto;

import java.util.UUID;

public record PaymentTypeModelDto(
        UUID paymentTypeId,
        String name
) {
}
