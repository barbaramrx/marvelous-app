package com.brb.marvelousapp.service;

import com.brb.marvelousapp.model.entity.User;

public interface UserService {

    User auth (String email, String password);

    User saveUser(User user);

    User updateUser(User user);

    User getLoggedUser(Long id);

    User getUserById(Long id);

    void validateEmail(String email);

}
