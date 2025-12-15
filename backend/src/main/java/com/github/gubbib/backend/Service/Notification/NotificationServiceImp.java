package com.github.gubbib.backend.Service.Notification;

import com.github.gubbib.backend.DTO.Notification.UserMyNotificationDTO;
import com.github.gubbib.backend.Domain.Notification.Notification;
import com.github.gubbib.backend.Domain.Notification.NotificationType;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.Notification.NotificationRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.User.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional(readOnly = true)
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserServiceImp userService;

    @Override
    public void create(User receiver, User sender, NotificationType type, String message, String targetUrl) {
        Notification notification = Notification.create(sender, receiver, type, message, targetUrl);

        notificationRepository.save(notification);
    }

    @Override
    public List<UserMyNotificationDTO> getMy(CustomUserPrincipal userPrincipal) {
        User user = userService.checkUser(userPrincipal);

        List<UserMyNotificationDTO> myNotificationList = notificationRepository.findMyNotification(user.getId());

        return myNotificationList;
    }

    @Override
    @Transactional
    public void notificationRead(CustomUserPrincipal userPrincipal, Long notificationId) {
        User user = userService.checkUser(userPrincipal);

        Notification notice = notificationRepository.findByIdAndReceiverIdAndIsDeletedFalse(notificationId, user.getId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if(!notice.isRead()) {
            notice.changeIsRead(true);
        }
    }

    @Override
    @Transactional
    public void notificationDelete(CustomUserPrincipal userPrincipal, Long notificationId) {
        User user = userService.checkUser(userPrincipal);

        Notification notice = notificationRepository.findByIdAndReceiverIdAndIsDeletedFalse(notificationId, user.getId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notice.changeIsDeleted(true);
    }
}
