package com.budgetPal.service.impl;

import com.budgetPal.dto.TransactionDto;
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
import com.budgetPal.utility.GetSignedEmail;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final GetSignedEmail getSignedEmail;
    private final ExpenseTypeRepository expenseTypeRepository;
    private final BudgetRepository budgetRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public TransactionDto transactionRegister (TransactionDto transactionDto) {

        User user = userRepository
                .findByEmail(getSignedEmail.getCurrentUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ExpenseType expenseType = expenseTypeRepository
                .findById(transactionDto.expenseTypeId())
                .orElseThrow(() -> new RuntimeException("Expense type not found"));

        PaymentType paymentType = paymentTypeRepository
                .findById(transactionDto.paymentTypeId())
                .orElseThrow(() -> new RuntimeException("Payment type not found"));

        Budget budget = budgetRepository
                .findById(transactionDto.budgetId())
                .orElseThrow(() -> new RuntimeException("budget not found"));

        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setBudget(budget);
        transaction.setExpenseType(expenseType);
        transaction.setPaymentType(paymentType);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDto(savedTransaction);
    }
}
