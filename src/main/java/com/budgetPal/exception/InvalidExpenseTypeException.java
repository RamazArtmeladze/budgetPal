package com.budgetPal.exception;

public class InvalidExpenseTypeException extends RuntimeException{
    public InvalidExpenseTypeException(String message) { super(message);}
}
