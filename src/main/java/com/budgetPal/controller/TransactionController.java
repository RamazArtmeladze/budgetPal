package com.budgetPal.controller;

import com.budgetPal.dto.TransactionDto;
import com.budgetPal.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/app/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/register")
    public ResponseEntity<?> transactionRegister(@RequestBody TransactionDto transactionDto) {

        TransactionDto transactionRegister = transactionService.transactionRegister(transactionDto);

        return new ResponseEntity<>(transactionRegister, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("id") UUID id) {
        transactionService.deleteTransaction(id);

        return new ResponseEntity<>("transaction deleted", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTransaction ( @RequestBody TransactionDto transactionDto) {

       TransactionDto updatedTransaction =  transactionService.updateTransaction(transactionDto);

        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }


    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getTransactionsByUserId(@PathVariable("userId") UUID userId, Pageable pageable) {
        Page<TransactionDto> transactions = transactionService.getTransactionsByUserId(pageable, userId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
