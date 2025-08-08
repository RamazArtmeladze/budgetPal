package com.budgetPal.service.impl;

import com.budgetPal.dto.PaymentTypeDto;
import com.budgetPal.mapper.PaymentTypeMapper;
import com.budgetPal.model.PaymentType;
import com.budgetPal.repository.PaymentTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentTypeServiceImplTest {

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    @Mock
    private PaymentTypeMapper paymentTypeMapper;

    @InjectMocks
    private PaymentTypeServiceImpl paymentTypeService;

    private PaymentTypeDto paymentTypeDto;
    private PaymentType paymentType;

    @BeforeEach
    void setUp() {
        paymentType = PaymentType.builder()
                .paymentTypeId(UUID.randomUUID())
                .name("card")
                .build();

        paymentTypeDto = new PaymentTypeDto(UUID.randomUUID(), "card");
    }

    @Test
    void paymentTypeRegister() {

        when(paymentTypeRepository.findByName("card")).thenReturn(Optional.empty());
        when(paymentTypeMapper.toEntity(paymentTypeDto)).thenReturn(paymentType);
        when(paymentTypeRepository.save(paymentType)).thenReturn(paymentType);
        when(paymentTypeMapper.toDto(paymentType)).thenReturn(paymentTypeDto);

        PaymentTypeDto result = paymentTypeService.paymentTypeRegister(paymentTypeDto);

        assertNotNull(result);
        assertEquals("card", result.name());

        verify(paymentTypeRepository).findByName("card");
        verify(paymentTypeRepository).save(paymentType);
        verify(paymentTypeMapper).toEntity(paymentTypeDto);
        verify(paymentTypeMapper).toDto(paymentType);
    }

    @Test
    void deletePaymentType() {

        when(paymentTypeRepository.findByName("card")).thenReturn(Optional.of(paymentType));

        paymentTypeService.deletePaymentType(paymentTypeDto);

        verify(paymentTypeRepository).deleteById(paymentType.getPaymentTypeId());
    }

    @Test
    void getAllPaymentType() {
        List<PaymentType> entities = List.of(paymentType);
        List<PaymentTypeDto> dtos = List.of(paymentTypeDto);

        when(paymentTypeRepository.findAll()).thenReturn(entities);
        when(paymentTypeMapper.toDto(entities)).thenReturn(dtos);

        List<PaymentTypeDto> result = paymentTypeService.getAllPaymentType();

        assertEquals(1, result.size());
        assertEquals("card", result.get(0).name());
    }
}