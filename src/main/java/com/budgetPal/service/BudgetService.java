package com.budgetPal.service;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;

import java.util.List;
import java.util.UUID;

public interface BudgetService {
    BudgetModelDto budgetRegister(BudgetDto budgetDto);

    List<BudgetModelDto> getBudgetByUserId (UUID userId);
}
