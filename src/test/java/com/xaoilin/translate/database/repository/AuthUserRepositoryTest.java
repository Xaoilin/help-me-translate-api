package com.xaoilin.translate.database.repository;

import com.xaoilin.translate.database.model.Status;
import com.xaoilin.translate.database.model.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void given_newUser_when_savingUser_then_savesSuccessfully() {
        // given
        AuthUser user = new AuthUser("test@gmail.com", "password", Status.PENDING_EMAIL_CONFIRMATION.name());

        // when
        userRepository.save(user);

        // then
        Optional<AuthUser> byEmail = userRepository.findByEmail("test@gmail.com");

        assertThat(byEmail).isPresent();
        assertThat(byEmail.get().getEmail()).isEqualTo("test@gmail.com");
    }


}