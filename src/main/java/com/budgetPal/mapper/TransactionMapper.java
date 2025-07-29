package com.budgetPal.mapper;

import com.budgetPal.dto.TransactionDto;
import com.budgetPal.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {java.time.LocalDateTime.class})
public interface TransactionMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "expenseType", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "budget", ignore = true)
    Transaction toEntity(TransactionDto transactionDto);

    @Mapping(target = "expenseTypeId", source = "expenseType.expenseTypeId")
    @Mapping(target = "paymentTypeId", source = "paymentType.paymentTypeId")
    @Mapping(target = "budgetId", source = "budget.budgetId")
    TransactionDto toDto(Transaction transaction);

}
