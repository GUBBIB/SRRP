package com.github.gubbib.backend.Repository.User;

import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Domain.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailAndRoleNot(String email, UserRole role);
    Optional<User> findByNameAndRoleNot(String name, UserRole role);
    Optional<User> findByNicknameAndRoleNot(String nickname, UserRole role);
    boolean existsByEmail(String email);
    boolean existsByNicknameAndRoleNot(String nickname,  UserRole role);
}
