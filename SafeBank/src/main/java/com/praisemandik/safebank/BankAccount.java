
package com.praisemandik.safebank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private String accountHolder;
    private BigDecimal balance;

    private final List<Transaction> transactions;

    private static final BigDecimal WITHDRAWAL_LIMIT =
            new BigDecimal("100000");


    public BankAccount(String accountHolder, BigDecimal openingBalance) {

        if (accountHolder == null || accountHolder.isBlank()) {
            throw new IllegalArgumentException(
                    "Account holder name cannot be blank."
            );
        }

        if (openingBalance == null ||
                openingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "Opening balance cannot be negative."
            );
        }

        this.accountHolder = accountHolder;
        this.balance = openingBalance;
        this.transactions = new ArrayList<>();
    }


    public void deposit(BigDecimal amount)
            throws InvalidAmountException {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero."
            );
        }

        balance = balance.add(amount);

        transactions.add(new Transaction("DEPOSIT", amount));
    }


    public void withdraw(BigDecimal amount)
            throws InvalidAmountException,
            InsufficientFundsException,
            WithdrawalLimitExceededException {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero."
            );
        }

        if (amount.compareTo(balance) > 0) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Your balance is " + balance
            );
        }

        if (amount.compareTo(WITHDRAWAL_LIMIT) > 0) {
            throw new WithdrawalLimitExceededException(
                    "Withdrawal exceeds the single withdrawal limit of ₦100,000."
            );
        }

        balance = balance.subtract(amount);

        transactions.add(new Transaction("WITHDRAWAL", amount));
    }


    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}
