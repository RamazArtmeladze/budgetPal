package com.budgetPal.service.impl;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.mapper.ExpenseTypeMapper;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.repository.ExpenseTypeRepository;
import com.budgetPal.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;

    @Override
    public ExpenseTypeDto expenseTypeRegister(ExpenseTypeDto expenseTypeDto) {

        ExpenseType savedExpenseType = expenseTypeRepository.save(expenseTypeMapper.toEntity(expenseTypeDto));

        return expenseTypeMapper.toDto(savedExpenseType);
    }

    @Override
    public void deleteExpenseType(String name) {
        expenseTypeRepository.deleteById(expenseTypeRepository.findByName(name).get().getExpenseTypeId());
    }
}
