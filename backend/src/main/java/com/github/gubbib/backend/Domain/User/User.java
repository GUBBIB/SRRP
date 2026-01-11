package com.github.gubbib.backend.Domain.User;

import com.github.gubbib.backend.Domain.Attendance.Attendance;
import com.github.gubbib.backend.Domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    @Column(name = "password", nullable = false,  length = 255)
    private String password;
    @Column(name = "name",  nullable = false,  length = 255)
    private String name;
    @Setter
    @Column(name = "nickname", nullable = false, length = 255, unique = true)
    private String nickname;

    @Column(name = "profile_image_url", length = 255)
    private String profile_image_url;

    @Column(name = "point", nullable = false)
    private Long point = 0L;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name="role", nullable = false,  length = 255, columnDefinition = "user_role")
    private UserRole role = UserRole.USER;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "provider", nullable = false, length = 255, columnDefinition = "provider_type")
    private Provider provider = Provider.LOCAL;

    @OneToMany(mappedBy = "user")
    private List<Attendance> attendances = new ArrayList<>();

    public static User createLocal(String email, String password, String name, String nickname){
        User u = new User();

        u.email = email;
        u.password = password;
        u.name = name;
        u.nickname = nickname;
        u.provider = Provider.LOCAL;
        u.point = 0L;

        return u;
    }

    public static User createOauth2(String email, String name, String nickname, Provider provider){
        User u = new User();

        u.email = email;
        u.password = UUID.randomUUID().toString();
        u.name = name;
        u.nickname = nickname;
        u.provider = provider;
        u.point = 0L;

        return u;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void addPoint(Long point){
        this.point += point;
    }

    public void usePoint(Long point){
        this.point -= point;
    }
}
