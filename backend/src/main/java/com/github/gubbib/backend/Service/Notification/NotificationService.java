package com.github.gubbib.backend.Service.Notification;

import com.github.gubbib.backend.DTO.Notification.UserMyNotificationDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface NotificationService {
    List<UserMyNotificationDTO> getMy(@AuthenticationPrincipal CustomUserPrincipal userPrincipal);
    void notificationRead(@AuthenticationPrincipal CustomUserPrincipal userPrincipal, @PathVariable Long notificationId);
}
