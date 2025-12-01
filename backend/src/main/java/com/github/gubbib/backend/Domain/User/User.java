package com.github.gubbib.backend.Domain.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User {
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
    @Column(name = "nickname", nullable = false, length = 255, unique = true)
    private String nickname;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false,  length = 255)
    private UserRole role = UserRole.USER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 255)
    private Provider provider = Provider.LOCAL;

    public User(String email, String password, String name, String nickname){
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = UserRole.USER;
    }
}
