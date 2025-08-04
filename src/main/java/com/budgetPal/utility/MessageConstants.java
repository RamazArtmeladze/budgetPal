package com.budgetPal.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageConstants {
    public static final  String USER_NOT_FOUND_BY_ID_MESSAGE = "user with provided id not found";
    public static final  String USER_NOT_FOUND_BY_EMAIL_MESSAGE = "user with provided email not found";

    public static final  String EXPENSE_TYPE_NOT_FOUND_MESSAGE = "expense type not found";
    public static final  String EXPENSE_TYPE_ALREADY_EXIST_MESSAGE = "expense type already exist";

    public static final  String BUDGET_NOT_FOUND_MESSAGE = "budget not found";

    public static final  String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "payment type not found";
    public static final  String PAYMENT_TYPE_ALREADY_EXIST_MESSAGE = "payment type already exist";

    public static final  String TRANSACTION_NOT_FOUND_MESSAGE = "transaction not found";
}
