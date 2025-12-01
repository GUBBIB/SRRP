package com.github.gubbib.backend.Repository;

import com.github.gubbib.backend.Domain.User.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> save(User user);
    List<User> findAll();
    
}
