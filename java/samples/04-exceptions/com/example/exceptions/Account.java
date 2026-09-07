package com.example.exceptions;

/**
 * 例外を投げる口座モデル。
 */
public class Account {
    private final String owner;
    private int balance;

    public Account(String owner, int initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("initialBalance must be >= 0");
        }
        this.owner = owner;
        this.balance = initialBalance;
    }

    public String getOwner() {
        return owner;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "残高不足: balance=" + balance + ", request=" + amount);
        }
        balance -= amount;
    }
}
