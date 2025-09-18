package com.maria.user.model;

public enum UserStatus {
    ACTIVE,    // can log in and use the app
    LOCKED,    // temporarily locked after too many failed attempts
    DISABLED   // permanently disabled (admin action)
}
