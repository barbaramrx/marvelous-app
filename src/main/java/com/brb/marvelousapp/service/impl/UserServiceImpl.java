package com.brb.marvelousapp.service.impl;

import com.brb.marvelousapp.exception.AuthError;
import com.brb.marvelousapp.exception.GetLoggedUserError;
import com.brb.marvelousapp.exception.MarvelousException;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.UserRepository;
import com.brb.marvelousapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public User auth(String email, String password) {
        Optional<User> user = repository.findByEmail(email);

        if (!user.isPresent()) {
            throw new AuthError("O usuário não foi encontrado.");
        }

        if (!user.get().getPassword().equals(password)) {
            throw new AuthError("Senha inválida.");
        }

        return user.get();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        validateEmail(user.getEmail());

        return repository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public User getLoggedUser(Long id) {
        boolean exists = repository.existsById(id);

        if (exists) {
            Optional<User> user = repository.findById(id);
            return user.get();
        } else {
            throw new GetLoggedUserError("Este usuário não existe.");
        }
    }

    @Override
    public void validateEmail(String email) {
        boolean exists = repository.existsByEmail(email);

        if (exists) {
            throw new MarvelousException("Este e-mail já possui uma conta vinculada.");
        }
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = repository.findById(id);

        if (!user.isPresent()) {
            throw new AuthError("O usuário não foi encontrado.");
        }

        return user.get();
    }
}
