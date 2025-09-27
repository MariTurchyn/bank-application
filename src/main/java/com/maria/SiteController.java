package com.maria;

import com.maria.account.model.Account;
import com.maria.account.service.AccountService;
import com.maria.common.LoginSession;
import com.maria.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SiteController {

    private final UserService users;
    private final AccountService accounts;
    private final LoginSession session;

    public SiteController(UserService users, AccountService accounts, LoginSession session) {
        this.users = users;
        this.accounts = accounts;
        this.session = session;
    }

    // ---------- Landing ----------
    @GetMapping("/")
    public String home(HttpSession http, Model model) {
        String me = session.currentUser(http);
        if (me != null) return "redirect:/dashboard";
        return "login";
    }

    // ---------- Auth (very basic) ----------
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           RedirectAttributes ra) {
        try {
            users.registerUser(username.trim(), email.trim(), password);
            ra.addFlashAttribute("msg", "Registered! Please log in.");
        } catch (Exception e) {
            ra.addFlashAttribute("err", "Username or email already exists.");
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession http,
                        RedirectAttributes ra) {
        if (users.login(username.trim(), password)) {
            session.login(http, username.trim());
            return "redirect:/dashboard";
        }
        ra.addFlashAttribute("err", "Invalid credentials");
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession http) {
        session.logout(http);
        return "redirect:/";
    }

    // ---------- Dashboard ----------
    @GetMapping("/dashboard")
    public String dashboard(HttpSession http, Model model) {
        String me = session.currentUser(http);
        if (me == null) return "redirect:/";
        List<Account> list = accounts.listForUsername(me);
        model.addAttribute("me", me);
        model.addAttribute("accounts", list);
        return "dashboard";
    }

    // ---------- Create account ----------
    @PostMapping("/accounts/create")
    public String createAccount(@RequestParam("type") String type,
                                @RequestParam(value = "currency", defaultValue = "USD") String currency,
                                HttpSession http,
                                RedirectAttributes ra) {
        String me = session.currentUser(http);
        if (me == null) return "redirect:/";
        try {
            accounts.createAccountForUsername(me, type, currency);
            ra.addFlashAttribute("msg", "Account created.");
        } catch (Exception e) {
            ra.addFlashAttribute("err", "Could not create account.");
        }
        return "redirect:/dashboard";
    }

    // ---------- Deposit / Withdraw ----------
    @PostMapping("/accounts/deposit")
    public String deposit(@RequestParam("accountNumber") String accountNumber,
                          @RequestParam("amount") BigDecimal amount,
                          RedirectAttributes ra) {
        try {
            accounts.deposit(accountNumber, amount);
            ra.addFlashAttribute("msg", "Deposit OK.");
        } catch (Exception e) {
            ra.addFlashAttribute("err", "Deposit failed.");
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/accounts/withdraw")
    public String withdraw(@RequestParam("accountNumber") String accountNumber,
                           @RequestParam("amount") BigDecimal amount,
                           RedirectAttributes ra) {
        try {
            accounts.withdraw(accountNumber, amount);
            ra.addFlashAttribute("msg", "Withdraw OK.");
        } catch (Exception e) {
            ra.addFlashAttribute("err", "Insufficient funds or error.");
        }
        return "redirect:/dashboard";
    }

    // ---------- Transfer ----------
    @PostMapping("/accounts/transfer")
    public String transfer(@RequestParam("from") String from,
                           @RequestParam("to") String to,
                           @RequestParam("amount") BigDecimal amount,
                           RedirectAttributes ra) {
        try {
            accounts.transfer(from, to, amount);
            ra.addFlashAttribute("msg", "Transfer OK.");
        } catch (Exception e) {
            ra.addFlashAttribute("err", "Transfer failed (check funds/numbers).");
        }
        return "redirect:/dashboard";
    }
}
