package com.budgetPal.mapper;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.model.ExpenseType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseTypeMapper {

    ExpenseType toEntity(ExpenseTypeDto expenseTypeDto);

    ExpenseTypeDto toDto(ExpenseType expenseType);

    List<ExpenseTypeDto> toDto(List<ExpenseType> expenseTypes);
}
