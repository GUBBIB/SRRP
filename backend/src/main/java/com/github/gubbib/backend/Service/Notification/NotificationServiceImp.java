package com.github.gubbib.backend.Service.Notification;

import com.github.gubbib.backend.DTO.Notification.UserMyNotificationDTO;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Repository.NotificationRepository.NotificationRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.User.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserServiceImp userService;


    @Override
    public List<UserMyNotificationDTO> getMy(CustomUserPrincipal userPrincipal) {
        User user = userService.checkUser(userPrincipal);

        List<UserMyNotificationDTO> myNotificationList = notificationRepository.findMyNotification(user.getId());

        return myNotificationList;
    }

}
