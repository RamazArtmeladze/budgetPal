package com.budgetPal.service.impl;

import com.budgetPal.dto.TransactionDto;
import com.budgetPal.exception.BudgetNotFoundException;
import com.budgetPal.exception.ExpenseTypeNotFoundException;
import com.budgetPal.exception.PaymentTypeNotFoundException;
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
import com.budgetPal.utility.GetSignedEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.budgetPal.utility.MessageConstants.BUDGET_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.EXPENSE_TYPE_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.PAYMENT_TYPE_NOT_FOUND_MESSAGE;
import static com.budgetPal.utility.MessageConstants.USER_NOT_FOUND_BY_EMAIL_MESSAGE;

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
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_BY_EMAIL_MESSAGE));

        ExpenseType expenseType = expenseTypeRepository
                .findById(transactionDto.expenseTypeId())
                .orElseThrow(() -> new ExpenseTypeNotFoundException(EXPENSE_TYPE_NOT_FOUND_MESSAGE));

        PaymentType paymentType = paymentTypeRepository
                .findById(transactionDto.paymentTypeId())
                .orElseThrow(() -> new PaymentTypeNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE));

        Budget budget = budgetRepository
                .findById(transactionDto.budgetId())
                .orElseThrow(() -> new BudgetNotFoundException(BUDGET_NOT_FOUND_MESSAGE));

        Transaction transaction = transactionMapper.toEntity(transactionDto);
        transaction.setBudget(budget);
        transaction.setExpenseType(expenseType);
        transaction.setPaymentType(paymentType);
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDto(savedTransaction);
    }
}
