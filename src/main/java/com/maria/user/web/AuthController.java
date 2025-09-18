package com.maria.user.web;

import com.maria.user.service.UserService;
import com.maria.user.web.dto.LoginRequest;
import com.maria.user.web.dto.RegisterRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService users;

    public AuthController(UserService users) {
        this.users = users;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        // ---- manual validation  ----
        if (req.getUsername() == null || req.getUsername().trim().length() < 3 || req.getUsername().length() > 30) {
            return ResponseEntity.badRequest().body("invalid username (3-30 chars required)");
        }
        if (req.getEmail() == null || !req.getEmail().contains("@") || req.getEmail().length() > 120) {
            return ResponseEntity.badRequest().body("invalid email");
        }
        if (req.getPassword() == null || req.getPassword().length() < 6 || req.getPassword().length() > 64) {
            return ResponseEntity.badRequest().body("invalid password (6-64 chars required)");
        }

        try {
            users.registerUser(req.getUsername().trim(), req.getEmail().trim(), req.getPassword());
            return ResponseEntity.ok("registered");
        } catch (DataIntegrityViolationException dup) {
            // thrown by JDBC when UNIQUE constraint (username/email) is violated
            return ResponseEntity.badRequest().body("username or email already exists");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("registration failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        if (req.getUsername() == null || req.getUsername().isBlank() ||
                req.getPassword() == null || req.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body("username and password required");
        }

        boolean ok = users.login(req.getUsername().trim(), req.getPassword());
        if (ok) return ResponseEntity.ok("login ok");
        return ResponseEntity.status(401).body("invalid credentials");
    }
}
