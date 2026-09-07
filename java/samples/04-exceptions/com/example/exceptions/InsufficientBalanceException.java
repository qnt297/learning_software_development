package com.example.exceptions;

/**
 * 残高不足を表すドメイン例外。
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
