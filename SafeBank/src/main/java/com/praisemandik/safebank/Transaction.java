
package com.praisemandik.safebank;
import java.math.BigDecimal;

import java.time.LocalDateTime;

public class Transaction {

    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;


    public Transaction(String transactionType, BigDecimal amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }


    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
}
