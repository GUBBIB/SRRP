package com.github.gubbib.backend.Handler;

import com.github.gubbib.backend.DTO.Admin.SystemNotificationEventDTO;
import com.github.gubbib.backend.DTO.Notification.CommentNotificationEventDTO;
import com.github.gubbib.backend.Domain.Notification.NotificationType;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Domain.User.UserRole;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Service.Notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationHandler {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @EventListener
    public void handleSystemNotification(SystemNotificationEventDTO event) {
        User systemUser = userRepository.findByRole(UserRole.SYSTEM);

        notificationService.create(
                event.receiver(),
                systemUser,
                NotificationType.SYSTEM,
                event.message(),
                event.targetUrl()
        );
    }

    @EventListener
    public void handleCommentNotification(CommentNotificationEventDTO event) {
        User receiver = event.receiver();
        User sender = event.sender();

        // 경로수정 필요
        String targetURL = "/boardId/" + event.boardId() + "/postId/" + event.postId();

        notificationService.create(
                receiver,
                sender,
                NotificationType.COMMENT,
                "게시글에 새로운 댓글이 달렸습니다.",
                targetURL // 경로수정 필요

        );
    }
}
