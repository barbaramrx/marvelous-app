package com.brb.marvelousapp.service;

import com.brb.marvelousapp.exception.AuthError;
import com.brb.marvelousapp.exception.MarvelousException;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.UserRepository;
import com.brb.marvelousapp.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @SpyBean
    UserServiceImpl service;

    @MockBean
    UserRepository repository;

    @Test
    public void successfullySaveUser() {
        Mockito.doNothing().when(service).validateEmail(Mockito.anyString());
        User user = User.builder()
                .userId(1L)
                .name("name")
                .email("email@email.com")
                .password("password")
                .build();

        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = service.saveUser(new User());

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUserId()).isEqualTo(1L);
        Assertions.assertThat(savedUser.getName()).isEqualTo("name");
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("password");
    }

//    @Test
//    public void emailAlreadyExistsError() {
//        String email = "email@email.com";
//        User user = User.builder().email(email).build();
//        Mockito.doThrow(MarvelousException.class).when(service).validateEmail(email);
//
//        service.saveUser(user);
//
//        Mockito.verify(repository, Mockito.never()).save(user);
//    }

    @Test
    public void successfullyAuthUser() {
        String email = "email@email.com";
        String password = "password";

        User user = User.builder().email(email).password(password).userId(1L).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = service.auth(email, password);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void emailNotFoundAuthError() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Throwable exp = Assertions.catchThrowable(()-> service.auth("email@email.com","password"));
        Assertions.assertThat(exp).isInstanceOf(AuthError.class).hasMessage("O usuário não foi encontrado.");
    }

    @Test
    public void wrongPasswordError() {
        String password = "password";
        User user = User.builder().email("email@email.com").password(password).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));

        Throwable exp = Assertions.catchThrowable(()-> service.auth("email@email.com", "test"));
        Assertions.assertThat(exp).isInstanceOf(AuthError.class).hasMessage("Senha inválida.");
    }

    @Test
    public void testEmailValidation() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        service.validateEmail("email@email.com");
    }

    @Test
    public void testEmailValidationException() {
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        Throwable exp = Assertions.catchThrowable(()->  service.validateEmail("usuario@email.com"));
        Assertions.assertThat(exp).isInstanceOf(MarvelousException.class).hasMessage("Este e-mail já tem uma conta vinculada.");
    }

}
