package com.budgetPal.service.impl;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.exception.ExpenseTypeNotFoundException;
import com.budgetPal.exception.UserNotFoundException;
import com.budgetPal.mapper.BudgetMapper;
import com.budgetPal.model.Budget;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.model.User;
import com.budgetPal.repository.BudgetRepository;
import com.budgetPal.repository.ExpenseTypeRepository;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.service.BudgetService;
import com.budgetPal.utility.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.budgetPal.utility.MessageConstants.EXPENSE_TYPE_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_ID_MESSAGE;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetMapper budgetMapper;
    private final BudgetRepository budgetRepository;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public BudgetModelDto budgetRegister(BudgetDto budgetDto) {

        User user = userRepository
                .findByEmail(currentUserProvider.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_ID_MESSAGE));

        List<ExpenseType> expenseTypes = expenseTypeRepository.findAllById(budgetDto.expenseTypeIds());
        if (expenseTypes.size() != budgetDto.expenseTypeIds().size()) {
            throw new RuntimeException(EXPENSE_TYPE_NOT_FOUND_MESSAGE);
        }

        Budget budget = budgetMapper.toEntity(budgetDto);
        budget.setUser(user);
        budget.setExpenseTypes(new HashSet<>(expenseTypes));
        Budget savedBudget = budgetRepository.save(budget);

        return budgetMapper.toDto(savedBudget);
    }

    @Override
    public List<BudgetModelDto> getBudgetByUserId(UUID userId) {

        List<Budget> budgets = budgetRepository.findByUserId(userId).stream().toList();

        return budgetMapper.toDto(budgets);
    }

    @Override
    public List<BudgetModelDto> getBudgetByExpenseType(String name) {

        List<Budget> budgets = budgetRepository.findByExpenseType(expenseTypeRepository.findByName(name).get().getExpenseTypeId());

        return budgetMapper.toDto(budgets);
    }
}
