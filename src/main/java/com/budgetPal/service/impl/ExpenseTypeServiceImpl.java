package com.budgetPal.service.impl;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.exception.ExpenseTypeAlreadyExistException;
import com.budgetPal.exception.ExpenseTypeNotFoundException;
import com.budgetPal.mapper.ExpenseTypeMapper;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.repository.ExpenseTypeRepository;
import com.budgetPal.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.budgetPal.utility.MessageConstants.EXPENSE_TYPE_ALREADY_EXIST_MESSAGE;
import static com.budgetPal.utility.MessageConstants.EXPENSE_TYPE_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeMapper expenseTypeMapper;

    @Override
    public ExpenseTypeDto expenseTypeRegister(ExpenseTypeDto expenseTypeDto) {

        expenseTypeRepository.findByName(expenseTypeDto.name())
                .ifPresent(p -> {
                    throw new ExpenseTypeAlreadyExistException(EXPENSE_TYPE_ALREADY_EXIST_MESSAGE);
                });

        ExpenseType savedExpenseType = expenseTypeRepository.save(expenseTypeMapper.toEntity(expenseTypeDto));

        return expenseTypeMapper.toDto(savedExpenseType);
    }

    @Override
    public void deleteExpenseType(ExpenseTypeDto expenseTypeDto) {

        ExpenseType expenseType = expenseTypeRepository.findByName(expenseTypeDto.name())
                .orElseThrow(() -> new ExpenseTypeNotFoundException(EXPENSE_TYPE_NOT_FOUND_MESSAGE));

        expenseTypeRepository.deleteById(expenseType.getExpenseTypeId());
    }

    @Override
    public List<ExpenseTypeDto> getAllExpenseType() {

        List<ExpenseType> expenseTypes = expenseTypeRepository.findAll();

        return expenseTypeMapper.toDto(expenseTypes);
    }
}
