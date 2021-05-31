package com.brb.marvelousapp.api.controller;

import com.brb.marvelousapp.api.dto.UserDTO;
import com.brb.marvelousapp.exception.AuthError;
import com.brb.marvelousapp.exception.GetLoggedUserError;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.UserRepository;
import com.brb.marvelousapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    private UserRepository repository;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestBody UserDTO dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        try {
            User authUser = service.auth(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(authUser);
        } catch (AuthError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .profile(dto.getProfile())
                .build();

        try {
            User savedUser = service.saveUser(user);
            return new ResponseEntity(savedUser, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        User updatedUser = service.getUserById(id);
        updatedUser.setName(dto.getName());
        updatedUser.setEmail(dto.getEmail());
        updatedUser.setPassword(dto.getPassword());

        try {
            User savedUser = service.updateUser(updatedUser);
            return new ResponseEntity(savedUser, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/logged")
    public ResponseEntity getLoggedUser(@PathVariable("id") Long id) {
        Optional<User> user = Optional.ofNullable(service.getLoggedUser(id));

        try {
            return ResponseEntity.ok(user.get().getName());
        } catch(GetLoggedUserError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
