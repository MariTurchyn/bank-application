package com.maria.account.service;

import com.maria.account.model.Account;
import com.maria.account.repo.AccountDao;
import com.maria.common.exceptions.InsufficientFundsException; // create if not present, or use RuntimeException
import com.maria.user.model.User;
import com.maria.user.repo.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountDao accounts;
    private final UserDao users;

    public AccountService(AccountDao accounts, UserDao users) {
        this.accounts = accounts;
        this.users = users;
    }

    /** Create a new account for the given username */
    public void createAccountForUsername(String username, String type, String currency) {
        User u = users.findByUsername(username);
        accounts.insert(u.getId(), type, currency);
    }

    /** List all accounts owned by the given username */
    public List<Account> listForUsername(String username) {
        User u = users.findByUsername(username);
        return accounts.findByUserId(u.getId());
    }


    /** Deposit money and return updated account */
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account a = accounts.findByNumber(accountNumber);
        accounts.addToBalance(a.getId(), amount);
        return accounts.findById(a.getId());
    }

    /** Withdraw money (throws if not enough) and return updated account */
    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account a = accounts.findByNumber(accountNumber);
        boolean ok = accounts.subtractIfEnough(a.getId(), amount);
        if (!ok) throw new InsufficientFundsException(); // or new RuntimeException("insufficient funds")
        return accounts.findById(a.getId());
    }

    /** Transfer money atomically from one account to another */
    @Transactional
    public void transfer(String fromAccount, String toAccount, BigDecimal amount) {
        if (fromAccount.equals(toAccount)) return; // no-op
        Account from = accounts.findByNumber(fromAccount);
        Account to   = accounts.findByNumber(toAccount);

        boolean ok = accounts.subtractIfEnough(from.getId(), amount);
        if (!ok) throw new InsufficientFundsException();
        accounts.addToBalance(to.getId(), amount);
    }
}
