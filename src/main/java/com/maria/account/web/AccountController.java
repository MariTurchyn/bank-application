package com.maria.account.web;

import com.maria.account.model.Account;
import com.maria.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accounts;

    public AccountController(AccountService accounts) {
        this.accounts = accounts;
    }

    /** Create a new account for a username */
    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestParam String username,
            @RequestParam String type,
            @RequestParam(defaultValue = "USD") String currency) {

        try {
            accounts.createAccountForUsername(username, type, currency);
            return ResponseEntity.ok("account created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("could not create account");
        }
    }

    /** List all accounts for a username */
    @GetMapping("/mine")
    public ResponseEntity<List<Account>> list(@RequestParam String username) {
        List<Account> list = accounts.listForUsername(username);
        return ResponseEntity.ok(list);
    }
}
