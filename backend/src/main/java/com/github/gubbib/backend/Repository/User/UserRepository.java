package com.github.gubbib.backend.Repository.User;

import com.github.gubbib.backend.DTO.User.UserMyPostDTO;
import com.github.gubbib.backend.Domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
}
