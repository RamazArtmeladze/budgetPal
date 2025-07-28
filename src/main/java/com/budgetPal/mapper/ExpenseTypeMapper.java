package com.budgetPal.mapper;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.model.ExpenseType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExpenseTypeMapper {

    ExpenseType toEntity(ExpenseTypeDto expenseTypeDto);

    ExpenseTypeDto toDto(ExpenseType expenseType);
}
