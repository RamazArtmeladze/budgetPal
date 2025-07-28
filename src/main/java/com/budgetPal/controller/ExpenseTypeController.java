package com.budgetPal.controller;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.service.ExpenseTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/expenseTypes")
@AllArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @PostMapping("/register")
    public ResponseEntity<?> expenseTypeRegister (@RequestBody ExpenseTypeDto expenseTypeDto) {

        ExpenseTypeDto expenseTypeRegister = expenseTypeService.expenseTypeRegister(expenseTypeDto);

        return new ResponseEntity<>(expenseTypeRegister, HttpStatus.CREATED);
    }
}
