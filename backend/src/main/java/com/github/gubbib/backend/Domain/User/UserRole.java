package com.github.gubbib.backend.Domain.User;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

}
