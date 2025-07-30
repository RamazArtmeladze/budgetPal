package com.budgetPal.service;

import com.budgetPal.dto.ExpenseTypeDto;

public interface ExpenseTypeService {
    ExpenseTypeDto expenseTypeRegister(ExpenseTypeDto expenseTypeDto);

    void deleteExpenseType (String name);
}
