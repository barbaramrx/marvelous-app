package com.brb.marvelousapp.api.controller;

import com.brb.marvelousapp.api.dto.ComicDTO;
import com.brb.marvelousapp.model.entity.Comic;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.ComicRepository;
import com.brb.marvelousapp.service.ComicService;
import com.brb.marvelousapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("{id}/save")
    public ResponseEntity save(@PathVariable("id")Long id, @RequestBody ComicDTO dto) {
        Comic comic = Comic.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();

        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        user.get().getFavComics().addAll(Arrays.asList(comic));

        try {
            Comic savedComic = service.saveComic(comic);
            userService.updateUser(user.get());

            return new ResponseEntity(savedComic, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/favComics")
    public ResponseEntity getFavComics(@PathVariable("id") Long id) {
        Optional<User> user = Optional.ofNullable(userService.getLoggedUser(id));

        try {
            return ResponseEntity.ok(user.get().getFavComics());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
