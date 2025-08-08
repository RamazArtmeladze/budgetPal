package com.budgetPal.service.impl;

import com.budgetPal.dto.ExpenseTypeDto;
import com.budgetPal.mapper.ExpenseTypeMapper;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.repository.ExpenseTypeRepository;
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
class ExpenseTypeServiceImplTest {

    @Mock
    private ExpenseTypeRepository expenseTypeRepository;

    @Mock
    private ExpenseTypeMapper expenseTypeMapper;

    @InjectMocks
    private ExpenseTypeServiceImpl expenseTypeService;

    private ExpenseTypeDto expenseTypeDto;
    private ExpenseType expenseType;

    @BeforeEach
    void setUp() {
        UUID expenseTypeId = UUID.randomUUID();

        expenseType = ExpenseType.builder()
                .expenseTypeId(expenseTypeId)
                .name("Food")
                .build();

        expenseTypeDto = new ExpenseTypeDto(expenseTypeId, "Food");
    }

    @Test
    void expenseTypeRegister() {

        when(expenseTypeRepository.findByName("Food")).thenReturn(Optional.empty());
        when(expenseTypeMapper.toEntity(expenseTypeDto)).thenReturn(expenseType);
        when(expenseTypeRepository.save(expenseType)).thenReturn(expenseType);
        when(expenseTypeMapper.toDto(expenseType)).thenReturn(expenseTypeDto);

        ExpenseTypeDto result = expenseTypeService.expenseTypeRegister(expenseTypeDto);

        assertNotNull(result);
        assertEquals("Food", result.name());

        verify(expenseTypeRepository).findByName("Food");
        verify(expenseTypeRepository).save(expenseType);
        verify(expenseTypeMapper).toEntity(expenseTypeDto);
        verify(expenseTypeMapper).toDto(expenseType);
    }

    @Test
    void deleteExpenseType() {

        when(expenseTypeRepository.findByName("Food")).thenReturn(Optional.of(expenseType));

        expenseTypeService.deleteExpenseType(expenseTypeDto);

        verify(expenseTypeRepository).findByName("Food");
        verify(expenseTypeRepository).deleteById(expenseType.getExpenseTypeId());
    }

    @Test
    void getAllExpenseType() {

        List<ExpenseType> entities = List.of(expenseType);
        List<ExpenseTypeDto> dtos = List.of(expenseTypeDto);

        when(expenseTypeRepository.findAll()).thenReturn(entities);
        when(expenseTypeMapper.toDto(entities)).thenReturn(dtos);

        List<ExpenseTypeDto> result = expenseTypeService.getAllExpenseType();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).name());
        assertEquals(expenseType.getExpenseTypeId(), result.get(0).expenseTypeId());

        verify(expenseTypeRepository).findAll();
        verify(expenseTypeMapper).toDto(entities);
    }
}