package com.budgetPal.mapper;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.model.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class})
public interface BudgetMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "expenseType", ignore = true)
    @Mapping(target = "user", ignore = true)
    Budget toEntity(BudgetDto budgetDto);

    @Mapping(target = "expenseType", source = "expenseType.expenseTypeId")
    BudgetModelDto toDto(Budget budget);
}
