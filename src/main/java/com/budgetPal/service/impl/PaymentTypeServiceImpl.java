package com.budgetPal.service.impl;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.mapper.PaymentTypeMapper;
import com.budgetPal.model.PaymentType;
import com.budgetPal.repository.PaymentTypeRepository;
import com.budgetPal.service.PaymentTypeService;
import com.budgetPal.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeMapper paymentTypeMapper;
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Override
    public PaymentTypeDto paymentTypeRegister(PaymentTypeDto paymentTypeDto) {

        PaymentType savedPaymentType =paymentTypeRepository.save(paymentTypeMapper.toEntity(paymentTypeDto));

        return paymentTypeMapper.toDto(savedPaymentType);
    }

    @Override
    public void deletePaymentType(PaymentTypeDto paymentTypeDto) {

        if (paymentTypeRepository.findByName(paymentTypeDto.name()).isPresent()) {

            paymentTypeRepository.deleteById(paymentTypeRepository.findByName
                    (paymentTypeDto.name()).get().getPaymentTypeId());

            logger.info("payment type {} deleted successfully", paymentTypeDto.name());

        } else {
            logger.info("Payment type '{}' not found, cannot delete", paymentTypeDto.name());
        }
    }

    @Override
    public List<PaymentTypeDto> getAllPaymentType() {
        List<PaymentType> paymentTypes =  paymentTypeRepository.findAll();

        return paymentTypeMapper.toDto(paymentTypes);
    }
}
