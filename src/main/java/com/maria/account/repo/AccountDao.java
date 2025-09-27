package com.maria.account.repo;

import com.maria.account.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

/**
 * Handles SQL queries for accounts table using JdbcTemplate.
 */
@Repository
public class AccountDao {

    private final JdbcTemplate jdbc;
    private final Random rnd = new Random();

    public AccountDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Helper: map SQL row -> Account object
    private Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account a = new Account();
        a.setId(rs.getLong("id"));
        a.setUserId(rs.getLong("user_id"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setType(rs.getString("type"));
        a.setCurrency(rs.getString("currency"));
        a.setBalance(rs.getBigDecimal("balance"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) a.setCreatedAt(ts.toInstant());
        return a;
    }

    /** Insert a new account for a user */
    public void insert(Long userId, String type, String currency) {
        String accountNumber = generateAccountNumber();
        jdbc.update(
                "INSERT INTO accounts (user_id, account_number, type, currency, balance) VALUES (?, ?, ?, ?, ?)",
                userId, accountNumber, type, currency, BigDecimal.ZERO
        );
    }

    /** List all accounts for a user */
    public List<Account> findByUserId(Long userId) {
        return jdbc.query(
                "SELECT * FROM accounts WHERE user_id = ? ORDER BY created_at DESC",
                this::mapRow,
                userId
        );
    }

    /** Find one account by DB id */
    public Account findById(Long id) {
        return jdbc.queryForObject(
                "SELECT * FROM accounts WHERE id = ?",
                this::mapRow, id
        );
    }

    /** Find one account by its human number */
    public Account findByNumber(String accountNumber) {
        return jdbc.queryForObject(
                "SELECT * FROM accounts WHERE account_number = ?",
                this::mapRow, accountNumber
        );
    }

    /** +amount (deposit) */
    public boolean addToBalance(long id, BigDecimal amount) {
        int updated = jdbc.update(
                "UPDATE accounts SET balance = balance + ? WHERE id = ?",
                amount, id
        );
        return updated == 1;
    }

    /** -amount only if enough money */
    public boolean subtractIfEnough(long id, BigDecimal amount) {
        int updated = jdbc.update(
                "UPDATE accounts SET balance = balance - ? WHERE id = ? AND balance >= ?",
                amount, id, amount
        );
        return updated == 1; // false => insufficient funds or bad id
    }

    // Simple 12-digit account number generator
    private String generateAccountNumber() {
        StringBuilder sb = new StringBuilder(12);
        for (int i = 0; i < 12; i++) sb.append(rnd.nextInt(10));
        return sb.toString();
    }
}
