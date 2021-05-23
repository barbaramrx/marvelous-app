package com.brb.marvelousapp.service;

import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.UserRepository;
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
public class UserServiceTest {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    @Test
    public void testEmailValidation() {
        //limpa database
        repository.deleteAll();

        //acao
        service.validateEmail("email@email.com");

    }

    @Test
    public void testEmailValidationException() {
        //limpa database
        repository.deleteAll();

        //inicializa user, nome e email para testar o metodo de validacao
        User user = User.builder().name("usuario").email("usuario@email.com").build();
        repository.save(user);

        service.validateEmail("usuario@email.com");
    }

}
