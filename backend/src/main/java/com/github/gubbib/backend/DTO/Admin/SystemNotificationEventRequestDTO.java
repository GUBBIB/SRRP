package com.github.gubbib.backend.DTO.Admin;

import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

@Builder
public record SystemNotificationEventRequestDTO(
        User receiver,
        String message,
        String targetUrl
) {
}
