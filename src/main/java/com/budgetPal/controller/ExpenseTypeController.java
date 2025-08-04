package com.budgetPal.controller;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/expenseTypes")
@RequiredArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> expenseTypeRegister (@RequestBody ExpenseTypeDto expenseTypeDto) {

        ExpenseTypeDto expenseTypeRegister = expenseTypeService.expenseTypeRegister(expenseTypeDto);

        return new ResponseEntity<>(expenseTypeRegister, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteExpenseType(@RequestBody ExpenseTypeDto expenseTypeDto) {

        expenseTypeService.deleteExpenseType(expenseTypeDto);

        return new ResponseEntity<>("Expense type deleted", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllExpenseType() {
        List<ExpenseTypeDto> allExpenseType = expenseTypeService.getAllExpenseType();

        return new ResponseEntity<>(allExpenseType,HttpStatus.OK);
    }
}
