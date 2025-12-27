package com.github.gubbib.backend.Repository;

import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Domain.User.UserRole;
import com.github.gubbib.backend.Repository.User.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
public class MemoryUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindById() {
        User user = User.createLocal("test1@gmail.com", "testtest", "test1", "testNickName");
        User saved = userRepository.save(user);
        Long savedId = saved.getId();

        Optional<User> result = userRepository.findById(savedId);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getEmail()).isEqualTo("test1@gmail.com");
        Assertions.assertThat(result.get().getName()).isEqualTo("test1");
        Assertions.assertThat(result.get().getNickname()).isEqualTo("testNickName");
    }

    @Test
    void saveAndFindByEmail() {
        User user = User.createLocal("test1@gmail.com", "testtest", "test1", "testNickName");
        User saved = userRepository.save(user);
        String savedEmail = saved.getEmail();

        Optional<User> result = userRepository.findByEmailAndRoleNot(savedEmail,  UserRole.SYSTEM);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getEmail()).isEqualTo(savedEmail);
        Assertions.assertThat(result.get().getName()).isEqualTo("test1");
        Assertions.assertThat(result.get().getNickname()).isEqualTo("testNickName");
    }

    @Test
    void saveAndFindByNickname() {
        User user = User.createLocal("test1@gmail.com", "testtest", "test1", "testNickName");
        User saved = userRepository.save(user);

        String savedNickname = saved.getNickname();

        Optional<User> result = userRepository.findByNicknameAndRoleNot(savedNickname,  UserRole.SYSTEM);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getNickname()).isEqualTo(savedNickname);
        Assertions.assertThat(result.get().getName()).isEqualTo("test1");
    }

    @Test
    void saveAndFindByName(){
        User user = User.createLocal("test1@gmail.com", "testtest", "test1", "testNickName");
        User saved = userRepository.save(user);
        String savedName = saved.getName();

        Optional<User> result = userRepository.findByNameAndRoleNot(savedName,  UserRole.SYSTEM);
        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().getName()).isEqualTo(savedName);
        Assertions.assertThat(result.get().getNickname()).isEqualTo("testNickName");
    }

    @Test
    void saveDuplication(){
        User user = User.createLocal("test1@gmail.com", "testtest", "test1", "testNickName");
        User saved = userRepository.save(user);
        User user2 = User.createLocal("test12@gmail.com", "testtest", "test1", "testNickName");

        Assertions.assertThatThrownBy(() -> userRepository.saveAndFlush(user2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}
