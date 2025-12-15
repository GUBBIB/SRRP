package com.github.gubbib.backend.DTO.Admin;

import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

@Builder
public record SystemNotificationEventDTO(
        User receiver,
        String message,
        String targetUrl
) {
}
