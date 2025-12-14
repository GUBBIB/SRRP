package com.github.gubbib.backend.DTO.Notification;

import com.github.gubbib.backend.Domain.Notification.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserMyNotificationDTO(
    Long notificationId,
    String message,
    boolean isRead,
    NotificationType type,
    Long senderId,
    String senderNickname,
    String targetURL,
    LocalDateTime createdAt
){

}
