package com.github.gubbib.backend.Domain.Notification;

import com.github.gubbib.backend.Domain.BaseEntity;
import com.github.gubbib.backend.Domain.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false, length = 255)
    private String message;
    @Column(name = "target_url", nullable = false, length = 255)
    private String targetUrl;
    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // 알림 받는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = true)
    private User sender; // 알림 보내는 사람

    public void changeIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
