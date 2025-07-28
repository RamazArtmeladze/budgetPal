package com.budgetPal.controller;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.service.BudgetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/budgets")
@AllArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/register")
    public ResponseEntity<?> budgetRegister (@RequestBody BudgetDto budgetDto){

       BudgetModelDto budgetModelDto = budgetService.budgetRegister(budgetDto);

        return new ResponseEntity<>(budgetModelDto, HttpStatus.CREATED);
    }
}
