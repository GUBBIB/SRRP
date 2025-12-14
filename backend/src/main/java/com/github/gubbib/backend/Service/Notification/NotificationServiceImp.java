package com.github.gubbib.backend.Service.Notification;

import com.github.gubbib.backend.Repository.NotificationRepository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;
}
