package com.brb.marvelousapp.api.controller;

import com.brb.marvelousapp.api.dto.ComicDTO;
import com.brb.marvelousapp.exception.GetLoggedUserError;
import com.brb.marvelousapp.model.entity.Comic;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.ComicRepository;
import com.brb.marvelousapp.model.repository.UserRepository;
import com.brb.marvelousapp.service.ComicService;
import com.brb.marvelousapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/comic")
public class ComicController {

    private ComicService service;

    private UserService userService;

    private ComicRepository repository;

    public ComicController(ComicService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ComicDTO dto) {

        Comic comic = Comic.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();

        Optional<User> user = Optional.ofNullable(userService.getUserById(dto.getUser_id()));
        user.get().getFavComics().add(comic);

        try {
            Comic savedComic = service.saveComic(comic);
            userService.updateUser(user.get());

            return new ResponseEntity(savedComic, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/fav/{id}")
    public ResponseEntity checkIfFav(@PathVariable("id") Long id) {
        boolean exists = repository.existsById(id);

        try {
            return ResponseEntity.ok(HttpStatus.FOUND);
        } catch(GetLoggedUserError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
