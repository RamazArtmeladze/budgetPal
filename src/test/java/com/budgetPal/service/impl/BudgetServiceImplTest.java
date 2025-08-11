package com.budgetPal.service.impl;

import com.budgetPal.dto.BudgetDto;
import com.budgetPal.dto.BudgetModelDto;
import com.budgetPal.mapper.BudgetMapper;
import com.budgetPal.model.Budget;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.model.User;
import com.budgetPal.repository.BudgetRepository;
import com.budgetPal.repository.ExpenseTypeRepository;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.utility.CurrentUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetServiceImplTest {

    @Mock
    private BudgetMapper budgetMapper;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ExpenseTypeRepository expenseTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private BudgetServiceImpl budgetService;

    private BudgetDto budgetDto;
    private Budget budget;
    private BudgetModelDto budgetModelDto;
    private User user;
    private ExpenseType expenseType;

    @BeforeEach
    void setUp() {
        UUID expenseTypeId = UUID.randomUUID();
        UUID budgetId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        expenseType = ExpenseType.builder()
                .expenseTypeId(expenseTypeId)
                .name("food")
                .build();

        budgetDto = new BudgetDto(
                "budget",
                BigDecimal.valueOf(1000),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                List.of(expenseTypeId)
        );

        budget = Budget.builder()
                .budgetId(budgetId)
                .name("budget")
                .totalAmount(BigDecimal.valueOf(1000))
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .createdAt(LocalDateTime.now())
                .expenseTypes(new HashSet<>(List.of(expenseType)))
                .user(User.builder().userId(userId).build())
                .build();

        budgetModelDto = new BudgetModelDto(
                budgetId,
                "budget",
                BigDecimal.valueOf(1000),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                List.of(expenseTypeId),
                LocalDateTime.now()
        );

        user = User.builder()
                .userId(userId)
                .email("test@test.com")
                .build();
    }

    @Test
    void budgetRegister() {

        when(currentUserProvider.getCurrentUserEmail()).thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(expenseTypeRepository.findAllById(budgetDto.expenseTypeIds())).thenReturn(List.of(expenseType));
        when(budgetMapper.toEntity(budgetDto)).thenReturn(budget);
        when(budgetRepository.save(budget)).thenReturn(budget);
        when(budgetMapper.toDto(budget)).thenReturn(budgetModelDto);

        BudgetModelDto result = budgetService.budgetRegister(budgetDto);

        assertNotNull(result);
        assertEquals("budget", result.name());

        verify(userRepository).findByEmail("test@test.com");
        verify(expenseTypeRepository).findAllById(budgetDto.expenseTypeIds());
        verify(budgetRepository).save(budget);
        verify(budgetMapper).toDto(budget);
    }


    @Test
    void getBudgetByUserId() {

        UUID userId = UUID.randomUUID();
        List<Budget> budgets = List.of(budget);
        List<BudgetModelDto> dtos = List.of(budgetModelDto);

        when(budgetRepository.findByUserId(userId)).thenReturn(budgets);
        when(budgetMapper.toDto(budgets)).thenReturn(dtos);

        List<BudgetModelDto> result = budgetService.getBudgetByUserId(userId);

        assertEquals(1, result.size());
        assertEquals("budget", result.get(0).name());

        verify(budgetRepository).findByUserId(userId);
        verify(budgetMapper).toDto(budgets);
    }

    @Test
    void getBudgetByExpenseType() {

        String expenseName = "food";
        List<Budget> budgets = List.of(budget);
        List<BudgetModelDto> dtos = List.of(budgetModelDto);

        when(expenseTypeRepository.findByName(expenseName)).thenReturn(Optional.of(expenseType));
        when(budgetRepository.findByExpenseType(expenseType.getExpenseTypeId())).thenReturn(budgets);
        when(budgetMapper.toDto(budgets)).thenReturn(dtos);

        List<BudgetModelDto> result = budgetService.getBudgetByExpenseType(expenseName);

        assertEquals(1, result.size());
        assertEquals("budget", result.get(0).name());

        verify(expenseTypeRepository).findByName(expenseName);
        verify(budgetRepository).findByExpenseType(expenseType.getExpenseTypeId());
        verify(budgetMapper).toDto(budgets);
    }
}