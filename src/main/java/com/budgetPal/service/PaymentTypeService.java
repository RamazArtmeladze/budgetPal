package com.budgetPal.service;

import com.budgetPal.dto.PaymentTypeDto;

import java.util.List;

public interface PaymentTypeService {
    PaymentTypeDto paymentTypeRegister (PaymentTypeDto paymentTypeDto);

    void  deletePaymentType (PaymentTypeDto paymentTypeDto);

    List<PaymentTypeDto> getAllPaymentType ();
}
