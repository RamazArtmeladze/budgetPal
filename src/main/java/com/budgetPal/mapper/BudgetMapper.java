package com.budgetPal.mapper;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.model.Budget;
import com.budgetPal.model.ExpenseType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "expenseTypes", ignore = true)
    @Mapping(target = "user", ignore = true)
    Budget toEntity(BudgetDto budgetDto);

    @Mapping(target = "expenseTypeIds", source = "expenseTypes")
    BudgetModelDto toDto(Budget budget);

    List<BudgetModelDto> toDto(List<Budget> budgets);

    default List<UUID> mapExpenseTypesToIds(Set<ExpenseType> expenseTypes) {
        if (expenseTypes == null) return null;
        return expenseTypes.stream()
                .map(ExpenseType::getExpenseTypeId)
                .toList();
    }
}
