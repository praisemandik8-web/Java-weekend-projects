package com.praisemandik.safebank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(
                "Praise",
                new BigDecimal("200000")
        );
    }


    @Test
    void testSuccessfulDeposit() throws InvalidAmountException {

        account.deposit(new BigDecimal("5000"));

        assertEquals(
                0,
                new BigDecimal("205000").compareTo(account.getBalance())
        );

        assertEquals(1, account.getTransactions().size());
    }


    @Test
    void testRejectNegativeDeposit() {

        assertThrows(
                InvalidAmountException.class,
                () -> account.deposit(new BigDecimal("-500"))
        );

        assertEquals(
                0,
                new BigDecimal("200000").compareTo(account.getBalance())
        );

        assertTrue(account.getTransactions().isEmpty());
    }


    @Test
    void testRejectZeroDeposit() {

        assertThrows(
                InvalidAmountException.class,
                () -> account.deposit(BigDecimal.ZERO)
        );

        assertTrue(account.getTransactions().isEmpty());
    }


    @Test
    void testInsufficientFunds() {

        assertThrows(
                InsufficientFundsException.class,
                () -> account.withdraw(new BigDecimal("250000"))
        );

        assertEquals(
                0,
                new BigDecimal("200000").compareTo(account.getBalance())
        );

        assertTrue(account.getTransactions().isEmpty());
    }


    @Test
    void testWithdrawalLimitExceeded() {

        assertThrows(
                WithdrawalLimitExceededException.class,
                () -> account.withdraw(new BigDecimal("120000"))
        );

        assertEquals(
                0,
                new BigDecimal("200000").compareTo(account.getBalance())
        );

        assertTrue(account.getTransactions().isEmpty());
    }


    @Test
    void testSuccessfulWithdrawal()
            throws InvalidAmountException,
            InsufficientFundsException,
            WithdrawalLimitExceededException {

        account.withdraw(new BigDecimal("50000"));

        assertEquals(
                0,
                new BigDecimal("150000").compareTo(account.getBalance())
        );

        assertEquals(1, account.getTransactions().size());
    }


    @Test
    void testSuccessfulTransactionAfterFailure()
            throws InvalidAmountException {

        assertThrows(
                InvalidAmountException.class,
                () -> account.deposit(new BigDecimal("-500"))
        );

        account.deposit(new BigDecimal("5000"));

        assertEquals(
                0,
                new BigDecimal("205000").compareTo(account.getBalance())
        );

        assertEquals(1, account.getTransactions().size());
    }


    @Test
    void testRejectBlankAccountHolder() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount(" ", new BigDecimal("1000"))
        );
    }


    @Test
    void testRejectNegativeOpeningBalance() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new BankAccount("Praise", new BigDecimal("-1000"))
        );
    }


    @Test
    void testTransactionHistoryIsUnmodifiable()
            throws InvalidAmountException {

        account.deposit(new BigDecimal("5000"));

        assertThrows(
                UnsupportedOperationException.class,
                () -> account.getTransactions().clear()
        );
    }
}
