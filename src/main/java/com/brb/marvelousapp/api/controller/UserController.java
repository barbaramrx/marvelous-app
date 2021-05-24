package com.brb.marvelousapp.api.controller;

import com.brb.marvelousapp.api.dto.UserDTO;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        try {
            User savedUser = service.saveUser(user);
            return new ResponseEntity(savedUser, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
