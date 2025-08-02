package com.budgetPal.controller;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/paymentTypes")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerPaymentType(@RequestBody PaymentTypeDto paymentTypeDto) {

        PaymentTypeDto paymentTypeRegister = paymentTypeService.paymentTypeRegister(paymentTypeDto);

        return new ResponseEntity<>(paymentTypeRegister, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePaymentType(@RequestBody PaymentTypeDto paymentTypeDto){

        paymentTypeService.deletePaymentType(paymentTypeDto);

        return new ResponseEntity<>("successfully deleted",HttpStatus.ACCEPTED);
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> gelAllPaymentType () {
        List<PaymentTypeDto> paymentTypes = paymentTypeService.getAllPaymentType();

        return new ResponseEntity<>(paymentTypes, HttpStatus.OK);
    }
}
