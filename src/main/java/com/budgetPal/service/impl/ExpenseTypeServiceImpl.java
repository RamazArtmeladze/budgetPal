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

        if (expenseTypeRepository.findByName(expenseTypeDto.name()).isPresent()) {
            throw new ExpenseTypeAlreadyExistException(EXPENSE_TYPE_ALREADY_EXIST_MESSAGE);
        }

        ExpenseType savedExpenseType = expenseTypeRepository.save(expenseTypeMapper.toEntity(expenseTypeDto));

        return expenseTypeMapper.toDto(savedExpenseType);
    }

    @Override
    public void deleteExpenseType(String name) {

        if (expenseTypeRepository.findByName(name).isPresent()) {
            expenseTypeRepository.deleteById(expenseTypeRepository.findByName(name).get().getExpenseTypeId());

        }else {
            throw new ExpenseTypeNotFoundException(EXPENSE_TYPE_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public List<ExpenseTypeDto> getAllExpenseType() {

        List<ExpenseType> expenseTypes = expenseTypeRepository.findAll();

        return expenseTypeMapper.toDto(expenseTypes);
    }
}
