package com.github.gubbib.backend.Domain.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    @Column(name = "password", nullable = false,  length = 255)
    private String password;
    @Column(name = "name",  nullable = false,  length = 255)
    private String name;
    @Column(name = "nickname", nullable = false, length = 255)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false,  length = 255)
    private UserRole role = UserRole.USER;

}
