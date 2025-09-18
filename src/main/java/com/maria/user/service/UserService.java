package com.maria.user.service;

import com.maria.user.model.User;
import com.maria.user.repo.UserDao;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Business logic for users: register, login, etc.
 */
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /** Register a new user (hash password first) */
    public void registerUser(String username, String email, String rawPassword) {
        // hash password with BCrypt
        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        // build User object
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPasswordHash(hash);

        // insert into DB
        userDao.insert(u);
    }

    /** Verify login credentials */
    public boolean login(String username, String rawPassword) {
        try {
            User user = userDao.findByUsername(username);
            // compare raw password with stored hash
            return BCrypt.checkpw(rawPassword, user.getPasswordHash());
        } catch (Exception e) {
            return false; // user not found or DB error
        }
    }
}
