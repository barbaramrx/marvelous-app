package com.brb.marvelousapp.service;

import com.brb.marvelousapp.model.entity.User;

public interface UserService {

    User auth (String email, String password);

    User saveUser(User user);

    void validateEmail(String email);

}
