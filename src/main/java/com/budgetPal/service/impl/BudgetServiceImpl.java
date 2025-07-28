package com.budgetPal.service.impl;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.mapper.BudgetMapper;
import com.budgetPal.model.Budget;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.model.User;
import com.budgetPal.repository.BudgetRepository;
import com.budgetPal.repository.ExpenseTypeRepository;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.service.BudgetService;
import com.budgetPal.utility.GetSignedEmail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final UserRepository userRepository;
    private final GetSignedEmail getSignedEmail;

    @Override
    public BudgetModelDto budgetRegister(BudgetDto budgetDto) {

        User user = userRepository
                .findByEmail(getSignedEmail.getCurrentUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExpenseType expenseType = expenseTypeRepository
                .findById(budgetDto.expenseTypeId())
                .orElseThrow(() -> new RuntimeException("Expense type not found"));

        Budget budget = budgetMapper.toEntity(budgetDto);
        budget.setUser(user);
        budget.setExpenseType(expenseType);
        Budget savedBudget = budgetRepository.save(budget);

        return budgetMapper.toDto(savedBudget);
    }
}
