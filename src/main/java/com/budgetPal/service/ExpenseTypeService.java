package com.budgetPal.service;

import com.budgetPal.dto.ExpenseTypeDto;

import java.util.List;

public interface ExpenseTypeService {
    ExpenseTypeDto expenseTypeRegister(ExpenseTypeDto expenseTypeDto);

    void deleteExpenseType (ExpenseTypeDto expenseTypeDto);

    List<ExpenseTypeDto> getAllExpenseType ();
}
