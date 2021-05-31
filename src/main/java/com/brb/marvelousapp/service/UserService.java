package com.brb.marvelousapp.service;

import com.brb.marvelousapp.model.entity.Char;
import com.brb.marvelousapp.model.entity.Comic;
import com.brb.marvelousapp.model.entity.User;

public interface UserService {

    User auth (String email, String password);

    User saveUser(User user);

    User updateUser(User user);

    User getLoggedUser(Integer id);

    User getUserById(Integer id);

    void validateEmail(String email);

    User deleteComic(Integer userId, Comic comic);

    User deleteCharacter(Integer userId, Char character);

}
