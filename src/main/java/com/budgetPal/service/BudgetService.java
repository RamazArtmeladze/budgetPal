package com.budgetPal.service;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;

public interface BudgetService {
    BudgetModelDto budgetRegister(BudgetDto budgetDto);
}
