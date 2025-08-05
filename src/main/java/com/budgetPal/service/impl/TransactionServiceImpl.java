package com.budgetPal.service.impl;

import com.budgetPal.dto.TransactionDto;
import com.budgetPal.exception.BudgetNotFoundException;
import com.budgetPal.exception.ExpenseTypeNotFoundException;
import com.budgetPal.exception.PaymentTypeNotFoundException;
import com.budgetPal.exception.TransactionNotFoundException;
import com.budgetPal.exception.UserNotFoundException;
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
import com.budgetPal.service.TransactionService;
import com.budgetPal.utility.CurrentUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.budgetPal.utility.MessageConstants.BUDGET_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.EXPENSE_TYPE_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.PAYMENT_TYPE_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.TRANSACTION_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_EMAIL_MESSAGE;
import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_ID_MESSAGE;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final BudgetRepository budgetRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public TransactionDto transactionRegister (TransactionDto transactionDto) {

        User user = findUser();

        ExpenseType expenseType = findExpenseType(transactionDto);

        PaymentType paymentType = findPaymentType(transactionDto);

        Budget budget = findBudget(transactionDto);

        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setBudget(budget);
        transaction.setExpenseType(expenseType);
        transaction.setPaymentType(paymentType);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDto(savedTransaction);
    }

    @Override
    public TransactionDto updateTransaction(TransactionDto transactionDto) {

        Transaction transaction = transactionRepository.findById(transactionDto.transactionId())
                .orElseThrow(() -> new TransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE));

        ExpenseType expenseType = findExpenseType(transactionDto);
        PaymentType paymentType = findPaymentType(transactionDto);
        Budget budget = findBudget(transactionDto);

        transaction.setAmount(transactionDto.amount());
        transaction.setTransactionDate(transactionDto.transactionDate());
        transaction.setExpenseType(expenseType);
        transaction.setPaymentType(paymentType);
        transaction.setDescription(transactionDto.description());
        transaction.setBudget(budget);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDto(savedTransaction);
    }

    @Override
    public void deleteTransaction(UUID id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE));

        transactionRepository.delete(transaction);
    }

    @Override
    public Page<TransactionDto> getTransactionsByUserId(Pageable pageable, UUID userId) {

        findUser();

        Page<Transaction> transactionsPage = transactionRepository.findByUserId(userId, pageable);

        return transactionsPage.map(transactionMapper::toDto);
    }

    @Override
    public TransactionDto getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(TRANSACTION_NOT_FOUND_MESSAGE));

        return transactionMapper.toDto(transaction);
    }

    @Override
    public Page<TransactionDto> getAll(Pageable pageable) {
        Page<Transaction> page = transactionRepository.findAll(pageable);

        return page.map(transactionMapper::toDto);
    }

    @Override
    public List<TransactionDto> getTransactionsByBudget(UUID budgetId) {
        List<Transaction> transactions = transactionRepository.findByBudgetBudgetId(budgetId);

        return transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionsByExpenseType(String name) {

        ExpenseType expenseType = expenseTypeRepository.findByName(name)
                .orElseThrow(() -> new ExpenseTypeNotFoundException(EXPENSE_TYPE_NOT_FOUND_MESSAGE));

        List<Transaction> transactions = transactionRepository.findByExpenseTypeExpenseTypeId(expenseType.getExpenseTypeId());

        return transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionsByPaymentType(String name) {

        PaymentType paymentType = paymentTypeRepository.findByName(name)
                .orElseThrow(() -> new PaymentTypeNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE));

        List<Transaction> transactions = transactionRepository.findByPaymentTypePaymentTypeId(paymentType.getPaymentTypeId());

        return transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getSumOfTransactionsAmountByBudget(UUID budgetId) {

        List<Transaction> transactions = transactionRepository.findByBudgetBudgetId(budgetId);

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getSumOfTransactionsAmountByExpenseType(UUID expenseTypeId) {

        List<Transaction> transactions = transactionRepository.findByExpenseTypeExpenseTypeId(expenseTypeId);

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getSumOfTransactionsAmountByPaymentType(UUID paymentTypeId) {

        List<Transaction> transactions = transactionRepository.findByPaymentTypePaymentTypeId(paymentTypeId);

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<TransactionDto> getTransactionsBetweenDates(LocalDate from, LocalDate to) {

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(from, to);

        return transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getSumBetweenDates(LocalDate from, LocalDate to) {

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(from, to);

        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private User findUser(){

        return userRepository
                .findByEmail(currentUserProvider.getCurrentUserEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE));
    }

    private ExpenseType findExpenseType(TransactionDto transactionDto) {

        return expenseTypeRepository
                .findById(transactionDto.expenseTypeId())
                .orElseThrow(() -> new ExpenseTypeNotFoundException(EXPENSE_TYPE_NOT_FOUND_MESSAGE));
    }

    private PaymentType findPaymentType(TransactionDto transactionDto){

        return paymentTypeRepository
                .findById(transactionDto.paymentTypeId())
                .orElseThrow(() -> new PaymentTypeNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE));
    }

    private Budget findBudget(TransactionDto transactionDto){

        return budgetRepository
                .findById(transactionDto.budgetId())
                .orElseThrow(() -> new BudgetNotFoundException(BUDGET_NOT_FOUND_MESSAGE));
    }
}
