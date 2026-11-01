package com.praisemandik.safebank;

public class WithdrawalLimitExceededException extends Exception {

    public WithdrawalLimitExceededException(String message) {
        super(message);
    }
}