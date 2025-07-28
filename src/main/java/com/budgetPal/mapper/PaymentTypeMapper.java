package com.budgetPal.mapper;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.dto.PaymentTypeModelDto;
import com.budgetPal.model.PaymentType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {

   PaymentType toEntity(PaymentTypeDto paymentTypeDto);

   PaymentTypeModelDto toDto(PaymentType paymentType);
}
