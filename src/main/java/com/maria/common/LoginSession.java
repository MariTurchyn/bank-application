package com.maria.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class LoginSession {
    public static final String KEY = "username";

    public void login(HttpSession session, String username) { session.setAttribute(KEY, username); }
    public void logout(HttpSession session) { session.removeAttribute(KEY); }
    public String currentUser(HttpSession session) {
        Object o = session.getAttribute(KEY);
        return o == null ? null : o.toString();
    }
    public boolean isLoggedIn(HttpSession session) { return currentUser(session) != null; }
}
