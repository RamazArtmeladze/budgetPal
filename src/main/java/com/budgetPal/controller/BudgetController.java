package com.budgetPal.controller;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/app/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/register")
    public ResponseEntity<?> budgetRegister (@RequestBody BudgetDto budgetDto){

       BudgetModelDto budgetModelDto = budgetService.budgetRegister(budgetDto);

        return new ResponseEntity<>(budgetModelDto, HttpStatus.CREATED);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<?> getBudgetByUserId (@PathVariable("id") UUID userId) {

        List<BudgetModelDto> budgets = budgetService.getBudgetByUserId(userId);

        return new ResponseEntity<>(budgets, HttpStatus.OK);
    }
}
