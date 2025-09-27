package com.maria.account.service;

import com.maria.account.model.Account;
import com.maria.account.repo.AccountDao;
import com.maria.user.model.User;
import com.maria.user.repo.UserDao;
import org.springframework.stereotype.Service;

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
        User u = users.findByUsername(username);   // SQL: SELECT * FROM users WHERE username=?
        accounts.insert(u.getId(), type, currency); // SQL: INSERT INTO accounts ...
    }

    /** List all accounts owned by the given username */
    public List<Account> listForUsername(String username) {
        User u = users.findByUsername(username);     // get user to find their id
        return accounts.findByUserId(u.getId());     // SQL: SELECT * FROM accounts WHERE user_id=?
    }
}
