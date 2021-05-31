package com.brb.marvelousapp.service.impl;

import com.brb.marvelousapp.exception.AuthError;
import com.brb.marvelousapp.exception.GetLoggedUserError;
import com.brb.marvelousapp.exception.MarvelousException;
import com.brb.marvelousapp.model.entity.Char;
import com.brb.marvelousapp.model.entity.Comic;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.UserCharRepository;
import com.brb.marvelousapp.model.repository.UserComicRepository;
import com.brb.marvelousapp.model.repository.UserRepository;
import com.brb.marvelousapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    private UserComicRepository userComicRepository;

    private UserCharRepository userCharRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, UserComicRepository userComicRepository, UserCharRepository userCharRepository) {
        super();
        this.repository = repository;
        this.userComicRepository = userComicRepository;
        this.userCharRepository = userCharRepository;
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
    public User getLoggedUser(Integer id) {
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
    public User getUserById(Integer id) {
        Optional<User> user = repository.findById(id);

        if (!user.isPresent()) {
            throw new AuthError("O usuário não foi encontrado.");
        }

        return user.get();
    }

    @Override
    @Transactional
    public User deleteComic(Integer userId, Comic comic) {
        boolean exists = userComicRepository.existsUsersByUserIdAndFavComics(userId, comic);

        if (exists) {
            User user = this.getUserById(userId);
            user.getFavComics().remove(comic);

            User freshUser = repository.save(user);
            return freshUser;
        } else {
            throw new MarvelousException("Esta comic não pertence ao user!");
        }
    }

    @Override
    @Transactional
    public User deleteCharacter(Integer userId, Char character) {
        boolean exists = userCharRepository.existsUsersByUserIdAndFavChars(userId, character);

        if (exists) {
            User user = this.getUserById(userId);
            user.getFavChars().remove(character);

            User freshUser = repository.save(user);
            return freshUser;
        } else {
            throw new MarvelousException("Este character não pertence ao user!");
        }
    }
}
