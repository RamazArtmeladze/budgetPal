package com.budgetPal.service.impl;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.exception.PaymentTypeAlreadyExistException;
import com.budgetPal.exception.PaymentTypeNotFoundException;
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

import static com.budgetPal.utility.MessageConstants.PAYMENT_TYPE_ALREADY_EXIST_MESSAGE;
import static com.budgetPal.utility.MessageConstants.PAYMENT_TYPE_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeMapper paymentTypeMapper;
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Override
    public PaymentTypeDto paymentTypeRegister(PaymentTypeDto paymentTypeDto) {

        if (paymentTypeRepository.findByName(paymentTypeDto.name()).isPresent()) {
            throw new PaymentTypeAlreadyExistException(PAYMENT_TYPE_ALREADY_EXIST_MESSAGE);
        }

        PaymentType savedPaymentType =paymentTypeRepository.save(paymentTypeMapper.toEntity(paymentTypeDto));

        return paymentTypeMapper.toDto(savedPaymentType);
    }

    @Override
    public void deletePaymentType(PaymentTypeDto paymentTypeDto) {

        if (paymentTypeRepository.findByName(paymentTypeDto.name()).isPresent()) {

            paymentTypeRepository.deleteById(paymentTypeRepository.findByName
                    (paymentTypeDto.name()).get().getPaymentTypeId());

        } else {
            throw new PaymentTypeNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public List<PaymentTypeDto> getAllPaymentType() {
        List<PaymentType> paymentTypes =  paymentTypeRepository.findAll();

        return paymentTypeMapper.toDto(paymentTypes);
    }
}
