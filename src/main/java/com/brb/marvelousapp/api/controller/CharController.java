package com.brb.marvelousapp.api.controller;

import com.brb.marvelousapp.api.dto.CharDTO;
import com.brb.marvelousapp.exception.FavError;
import com.brb.marvelousapp.model.entity.Char;
import com.brb.marvelousapp.model.entity.User;
import com.brb.marvelousapp.model.repository.ComicRepository;
import com.brb.marvelousapp.service.CharService;
import com.brb.marvelousapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/char")
public class CharController {

    private CharService service;

    private UserService userService;

    private ComicRepository repository;

    public CharController(CharService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("{id}/save")
    public ResponseEntity save(@PathVariable("id")Integer id, @RequestBody CharDTO dto) {

        Char character = Char.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();

        service.checkIfExists(character);

        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        user.get().getFavChars().addAll(Arrays.asList(character));
        User favdChar = userService.updateUser(user.get());

        try {
            return new ResponseEntity(favdChar, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/favCharacters")
    public ResponseEntity checkIfFav(@PathVariable("id") Integer id) {
        Optional<User> user = Optional.ofNullable(userService.getLoggedUser(id));

        try {
            return ResponseEntity.ok(user.get().getFavChars());
        } catch(FavError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{idUser}/{idChar}/delete")
    public ResponseEntity deleteComicFav(@PathVariable("idUser")Integer idUser, @PathVariable("idChar")Long idChar) {

        Char getChar = service.getCharById(idChar);

        try {
            userService.deleteCharacter(idUser, getChar);
            return ResponseEntity.ok(HttpStatus.GONE);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
