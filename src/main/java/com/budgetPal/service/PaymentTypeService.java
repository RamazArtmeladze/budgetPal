package com.budgetPal.service;

import com.budgetPal.dto.PaymentTypeDto;

public interface PaymentTypeService {
    PaymentTypeDto paymentTypeRegister (PaymentTypeDto paymentTypeDto);

    void  deletePaymentType (PaymentTypeDto paymentTypeDto);
}
