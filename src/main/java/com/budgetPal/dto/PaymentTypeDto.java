package com.budgetPal.dto;

import java.util.UUID;

public record PaymentTypeDto(
        UUID paymentTypeId,
        String name
) {
}
