package com.github.gubbib.backend.Domain.Notification;

import lombok.Getter;

@Getter
public enum NotificationType {
    COMMENT("COMMENT"),
    FOLLOW("FOLLOW"),
    SYSTEM("SYSTEM");

    private final String type;

    NotificationType(String type) {
        this.type = type;
    }
}
