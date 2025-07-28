package com.budgetPal.controller;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.dto.PaymentTypeModelDto;
import com.budgetPal.service.PaymentTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/paymentTypes")
@AllArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @PostMapping("/register")
    public ResponseEntity<?> registerPaymentType(@RequestBody PaymentTypeDto paymentTypeDto) {

        PaymentTypeModelDto paymentTypeModelDto = paymentTypeService.paymentTypeRegister(paymentTypeDto);

        return new ResponseEntity<>(paymentTypeModelDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePaymentType(@RequestBody PaymentTypeDto paymentTypeDto){

        paymentTypeService.deletePaymentType(paymentTypeDto);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
