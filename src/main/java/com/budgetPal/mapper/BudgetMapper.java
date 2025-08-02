package com.budgetPal.mapper;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.model.Budget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "expenseType", ignore = true)
    @Mapping(target = "user", ignore = true)
    Budget toEntity(BudgetDto budgetDto);

    @Mapping(target = "expenseTypeId", source = "expenseType.expenseTypeId")
    BudgetModelDto toDto(Budget budget);

    List<BudgetModelDto> toDto(List<Budget> budgets);
}
