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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable ("id") UUID id) {
        TransactionDto dto = transactionService.getTransactionById(id);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions(Pageable pageable) {
        Page<TransactionDto> page = transactionService.getAll(pageable);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/byBudget/{budgetId}")
    public ResponseEntity<?> getTransactionsByBudget(@PathVariable ("budgetId") UUID budgetId) {

        List<TransactionDto> transactions = transactionService.getTransactionsByBudget(budgetId);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/byExpenseType/{name}")
    public ResponseEntity<?> getTransactionsByExpenseType(@PathVariable ("name") String name) {
        List<TransactionDto> transactions = transactionService.getTransactionsByExpenseType(name);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/byPaymentType/{name}")
    public ResponseEntity<?> getTransactionsByPaymentType(@PathVariable("name") String name) {
        List<TransactionDto> transactions = transactionService.getTransactionsByPaymentType(name);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/sum/byBudget/{budgetId}")
    public ResponseEntity<?> getSumOfTransactionsByBudget(@PathVariable("budgetId") UUID budgetId) {
        BigDecimal sum = transactionService.getSumOfTransactionsAmountByBudget(budgetId);

        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @GetMapping("/sum/byExpenseType/{expenseTypeId}")
    public ResponseEntity<?> getSumOfTransactionsByExpenseType(@PathVariable("expenseTypeId") UUID expenseTypeId) {
        BigDecimal sum = transactionService.getSumOfTransactionsAmountByExpenseType(expenseTypeId);

        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @GetMapping("/getSpentPercentageByBudget/{budgetId}")
    public ResponseEntity<?> getSpentPercentageByBudget (@PathVariable("budgetId") UUID budgetId) {

        BigDecimal percentage = transactionService.getSpentPercentageByBudget(budgetId);

        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    @GetMapping("/sum/byPaymentType/{paymentTypeId}")
    public ResponseEntity<BigDecimal> getSumOfTransactionsByPaymentType(@PathVariable ("paymentTypeId")  UUID paymentTypeId) {
        BigDecimal sum = transactionService.getSumOfTransactionsAmountByPaymentType(paymentTypeId);

        return new ResponseEntity<>(sum, HttpStatus.OK);
    }

    @GetMapping("/betweenDates")
    public ResponseEntity<List<TransactionDto>> getTransactionsBetweenDates(@RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {

        List<TransactionDto> transactions = transactionService.getTransactionsBetweenDates(from, to);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/sum/betweenDates")
    public ResponseEntity<BigDecimal> getSumBetweenDates(@RequestParam("from") LocalDate from, @RequestParam("to") LocalDate to) {
        BigDecimal sum = transactionService.getSumBetweenDates(from, to);

        return new ResponseEntity<>(sum, HttpStatus.OK);
    }
}
