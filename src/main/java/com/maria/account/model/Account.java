package com.maria.account.model;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Plain Java object representing a bank account row.
 */
public class Account {
    private Long id;              // DB primary key
    private Long userId;          // owner (FK to users.id)
    private String accountNumber; // human-readable number, unique
    private String type;          //  "CHECKING" or "SAVINGS"
    private String currency;      //  "USD"
    private BigDecimal balance;   // money; use BigDecimal
    private Instant createdAt;    // when account was created

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
