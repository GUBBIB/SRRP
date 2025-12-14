package com.github.gubbib.backend.Repository.NotificationRepository;

import com.github.gubbib.backend.Domain.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
