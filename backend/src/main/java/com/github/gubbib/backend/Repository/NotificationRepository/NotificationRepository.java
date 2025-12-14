package com.github.gubbib.backend.Repository.NotificationRepository;

import com.github.gubbib.backend.DTO.Notification.UserMyNotificationDTO;
import com.github.gubbib.backend.Domain.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
        SELECT new com.github.gubbib.backend.DTO.Notification.UserMyNotificationDTO(
                n.id,
                n.message,
                n.isRead,
                n.type,
                n.sender.id,
                n.sender.nickname,
                n.targetUrl,
                n.createdAt
            )
        FROM Notification n
        JOIN n.receiver u
        WHERE u.id = :userId 
            AND n.isDeleted = false
        ORDER BY n.createdAt DESC
    """)
    List<UserMyNotificationDTO> findMyNotification(Long userId);
}