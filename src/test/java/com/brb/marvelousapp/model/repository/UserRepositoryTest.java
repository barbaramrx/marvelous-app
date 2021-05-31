package com.brb.marvelousapp.model.repository;

import com.brb.marvelousapp.model.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void checkIfEmailExists() {
        //inicializa user, nome e email para testar o metodo existsByEmail
        User user = buildUser();
        entityManager.persist(user);

        //testa o metodo existsByEmail com o email informado acima
        boolean result = repository.existsByEmail("user@email.com");

        //testa se o resultado retornado isTrue
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void checkIfEmailDoesntExist() {
        //testa o metodo existsByEmail com o email informado acima
        boolean result = repository.existsByEmail("user@email.com");

        //testa se o resultado retornado isTrue
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void persistUserOnDatabase() {
        User user = buildUser();
        User savedUser = repository.save(user);
        Assertions.assertThat(savedUser.getUserId()).isNotNull();
    }

    @Test
    public void getUserByEmail() {
        User user = buildUser();
        entityManager.persist(user);
        Optional<User> result = repository.findByEmail("user@email.com");
        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void getUserByEmailMustReturnEmpty() {
        Optional<User> result = repository.findByEmail("user@email.com");
        Assertions.assertThat(result.isPresent()).isFalse();
    }

    public static User buildUser() {
        return User
                .builder()
                .name("user")
                .email("user@email.com")
                .password("password")
                .build();
    }
}
