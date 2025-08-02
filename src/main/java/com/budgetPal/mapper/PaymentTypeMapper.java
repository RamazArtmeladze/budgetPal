package com.budgetPal.mapper;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentTypeMapper {

   PaymentType toEntity(PaymentTypeDto paymentTypeDto);

   PaymentTypeDto toDto(PaymentType paymentType);
}
