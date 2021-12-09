package com.example.coachwithme.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole {
    COACHEE("COACHEE"), COACH("COACH"), ADMIN("ADMIN");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
