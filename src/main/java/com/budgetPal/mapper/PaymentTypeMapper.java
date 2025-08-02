package com.budgetPal.mapper;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.model.PaymentType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentTypeMapper {

   PaymentType toEntity(PaymentTypeDto paymentTypeDto);

   PaymentTypeDto toDto(PaymentType paymentType);

   List<PaymentTypeDto> toDto (List<PaymentType> paymentTypes);
}
