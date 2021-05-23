package com.brb.marvelousapp.model.repository;

import com.brb.marvelousapp.model.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void checkIfEmailExists() {
        //limpa database
        repository.deleteAll();

        //inicializa user, nome e email para testar o metodo existsByEmail
        User user = User.builder().name("usuario").email("usuario@email.com").build();
        repository.save(user);

        //testa o metodo existsByEmail com o email informado acima
        boolean result = repository.existsByEmail("usuario@email.com");

        //testa se o resultado retornado isTrue
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void checkIfEmailDoesntExist() {
        //limpa database
        repository.deleteAll();

        //testa o metodo existsByEmail com o email informado acima
        boolean result = repository.existsByEmail("usuario@email.com");

        //testa se o resultado retornado isTrue
        Assertions.assertThat(result).isFalse();
    }

}
