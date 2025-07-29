package com.budgetPal.controller;

import com.budgetPal.dto.TransactionDto;
import com.budgetPal.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/transactions")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/register")
    public ResponseEntity<?> transactionRegister (@RequestBody TransactionDto transactionDto) {

        TransactionDto transactionRegister = transactionService.transactionRegister(transactionDto);

        return new ResponseEntity<>(transactionRegister ,HttpStatus.CREATED);
    }

}
