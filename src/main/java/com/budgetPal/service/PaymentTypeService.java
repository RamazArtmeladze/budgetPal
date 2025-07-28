package com.budgetPal.service;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.dto.PaymentTypeModelDto;

public interface PaymentTypeService {

    PaymentTypeModelDto paymentTypeRegister (PaymentTypeDto paymentTypeDto);

    void  deletePaymentType (PaymentTypeDto paymentTypeDto);
}
