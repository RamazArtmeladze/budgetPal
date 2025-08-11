package com.budgetPal.service.impl;

import com.budgetPal.dto.TransactionDto;
import com.budgetPal.exception.BudgetNotFoundException;
import com.budgetPal.mapper.TransactionMapper;
import com.budgetPal.model.Budget;
import com.budgetPal.model.ExpenseType;
import com.budgetPal.model.PaymentType;
import com.budgetPal.model.Transaction;
import com.budgetPal.model.User;
import com.budgetPal.repository.BudgetRepository;
import com.budgetPal.repository.ExpenseTypeRepository;
import com.budgetPal.repository.PaymentTypeRepository;
import com.budgetPal.repository.TransactionRepository;
import com.budgetPal.repository.UserRepository;
import com.budgetPal.utility.CurrentUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock private TransactionRepository transactionRepository;
    @Mock private BudgetRepository budgetRepository;
    @Mock private TransactionMapper transactionMapper;
    @Mock private UserRepository userRepository;
    @Mock private ExpenseTypeRepository expenseTypeRepository;
    @Mock private PaymentTypeRepository paymentTypeRepository;
    @Mock private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private UUID transactionId;
    private UUID budgetId;
    private UUID expenseTypeId;
    private UUID paymentTypeId;

    private Transaction transaction;
    private TransactionDto transactionDto;
    private ExpenseType expenseType;


    @BeforeEach
    void setUp() {

        expenseType = ExpenseType.builder()
                .expenseTypeId(expenseTypeId)
                .name("food")
                .build();

        transactionId = UUID.randomUUID();
        budgetId = UUID.randomUUID();
        expenseTypeId = UUID.randomUUID();
        paymentTypeId = UUID.randomUUID();

        transaction = Transaction.builder()
                .transactionId(transactionId)
                .amount(new BigDecimal("100.00"))
                .transactionDate(LocalDate.of(2025, 8, 1))
                .budget(Budget.builder().
                        budgetId(budgetId)
                        .totalAmount(new BigDecimal("500.00"))
                        .expenseTypes(new HashSet<>(List.of(expenseType)))
                .build())
                .build();

        transactionDto = new TransactionDto(
                transactionId,
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription(),
                expenseTypeId,
                paymentTypeId,
                budgetId
        );
    }

    @Test
    void transactionRegister() {

        User user = new User();
        ExpenseType expenseType = new ExpenseType();
        PaymentType paymentType = new PaymentType();
        Budget budget = Budget.builder().budgetId(budgetId).expenseTypes(Set.of(expenseType)).build();
        Transaction toSave = new Transaction();

        when(currentUserProvider.getCurrentUserEmail()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(expenseTypeRepository.findById(expenseTypeId)).thenReturn(Optional.of(expenseType));
        when(paymentTypeRepository.findById(paymentTypeId)).thenReturn(Optional.of(paymentType));
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(budget));
        when(transactionMapper.toEntity(transactionDto)).thenReturn(toSave);
        when(transactionRepository.save(toSave)).thenReturn(transaction);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);

        TransactionDto result = transactionService.transactionRegister(transactionDto);

        assertThat(result).isNotNull();
        assertThat(result.transactionId()).isEqualTo(transactionId);
    }

    @Test
    void getTransactionsByUserId() {
        UUID userId = UUID.randomUUID();
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Transaction> page = new PageImpl<>(List.of(transaction));

        when(currentUserProvider.getCurrentUserEmail()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        when(transactionRepository.findByUserId(userId, pageable)).thenReturn(page);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);

        Page<TransactionDto> result = transactionService.getTransactionsByUserId(pageable, userId);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).transactionId()).isEqualTo(transactionId);

    }

    @Test
    void getTransactionById() {
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);

        TransactionDto result = transactionService.getTransactionById(transactionId);

        assertThat(result).isNotNull();
        assertThat(result.transactionId()).isEqualTo(transactionId);
    }

    @Test
    void getAll() {

        Page<Transaction> transactions = new PageImpl<>(List.of(transaction));
        PageRequest pageable = PageRequest.of(0, 10);

        when(transactionRepository.findAll(pageable)).thenReturn(transactions);
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);

        Page<TransactionDto> result = transactionService.getAll(pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).transactionId()).isEqualTo(transactionId);
    }

    @Test
    void getTransactionsByBudget() {

        when(transactionRepository.findByBudgetBudgetId(budgetId)).thenReturn(List.of(transaction));
        when(transactionMapper.toDto(transaction)).thenReturn(transactionDto);

        List<TransactionDto> result = transactionService.getTransactionsByBudget(budgetId);

        assertThat(result.get(0).transactionId()).isEqualTo(transactionId);
    }


    @Test
    void getSumOfTransactionsAmountByBudget() {
        when(transactionRepository.findByBudgetBudgetId(budgetId)).thenReturn(List.of(transaction));

        BigDecimal result = transactionService.getSumOfTransactionsAmountByBudget(budgetId);

        assertThat(result).isEqualByComparingTo("100.00");
    }

    @Test
    void getSpentPercentageByBudget() {
        when(transactionRepository.findByBudgetBudgetId(budgetId)).thenReturn(List.of(transaction));
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.of(transaction.getBudget()));

        BigDecimal result = transactionService.getSpentPercentageByBudget(budgetId);

        assertThat(result).isEqualByComparingTo("20.00");
    }

    @Test
    void getSpentPercentageByBudget_shouldThrow_whenBudgetNotFound() {
        when(transactionRepository.findByBudgetBudgetId(budgetId)).thenReturn(Collections.emptyList());
        when(budgetRepository.findById(budgetId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.getSpentPercentageByBudget(budgetId))
                .isInstanceOf(BudgetNotFoundException.class);
    }

    @Test
    void getSumOfTransactionsAmountByExpenseType() {
        when(transactionRepository.findByExpenseTypeExpenseTypeId(expenseTypeId)).thenReturn(List.of(transaction));

        BigDecimal result = transactionService.getSumOfTransactionsAmountByExpenseType(expenseTypeId);

        assertThat(result).isEqualByComparingTo("100.00");
    }

    @Test
    void getSumOfTransactionsAmountByPaymentType() {
        when(transactionRepository.findByPaymentTypePaymentTypeId(paymentTypeId)).thenReturn(List.of(transaction));

        BigDecimal result = transactionService.getSumOfTransactionsAmountByPaymentType(paymentTypeId);

        assertThat(result).isEqualByComparingTo("100.00");
    }
}