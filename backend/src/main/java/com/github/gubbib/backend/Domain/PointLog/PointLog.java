package com.github.gubbib.backend.Domain.PointLog;

import com.github.gubbib.backend.Domain.BaseEntity;
import com.github.gubbib.backend.Domain.User.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "point_log")
public class PointLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;
    @Column(name = "type", nullable = false,  length = 50)
    private String type;
    @Column(name = "description",  nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static PointLog earn(User user, Long amount, String description) {
        PointLog pl = new PointLog();

        pl.user = user;
        pl.amount = amount;
        pl.type = "EARN";
        pl.description = description;

        return pl;
    }

    public static PointLog use(User user, Long amount, String description) {
        PointLog pl = new PointLog();

        pl.user = user;
        pl.amount = -amount;
        pl.type = "USE";
        pl.description = description;

        return pl;
    }
}
