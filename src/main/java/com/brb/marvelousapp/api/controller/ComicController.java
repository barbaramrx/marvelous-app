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

@CrossOrigin(origins = "https://the-marvelous-app.herokuapp.com")
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
    public ResponseEntity save(@PathVariable("id")Integer id, @RequestBody ComicDTO dto) {

        Comic comic = Comic.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();

        service.checkIfExists(comic);

        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        user.get().getFavComics().addAll(Arrays.asList(comic));
        User favdComic = userService.updateUser(user.get());

        try {
            return new ResponseEntity(favdComic, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{id}/favComics")
    public ResponseEntity getFavComics(@PathVariable("id") Integer id) {
        Optional<User> user = Optional.ofNullable(userService.getLoggedUser(id));

        try {
            return ResponseEntity.ok(user.get().getFavComics());
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{idUser}/{idComic}/delete")
    public ResponseEntity deleteComicFav(@PathVariable("idUser")Integer idUser, @PathVariable("idComic")Long idComic) {

        Comic getComic = service.getComicById(idComic);

        try {
            userService.deleteComic(idUser, getComic);
            return ResponseEntity.ok(HttpStatus.GONE);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
