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

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.Optional;

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
    public ResponseEntity save(@PathVariable("id")Long id, @RequestBody CharDTO dto) {
        Char character = Char.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();

        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        user.get().getFavChars().addAll(Arrays.asList(character));

        try {
            Char savedChar = service.saveChar(character);
            userService.updateUser(user.get());

            return new ResponseEntity(savedChar, HttpStatus.CREATED);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/favCharacters")
    public ResponseEntity checkIfFav(@PathVariable("id") Long id) {
        Optional<User> user = Optional.ofNullable(userService.getLoggedUser(id));

        try {
            return ResponseEntity.ok(user.get().getFavChars());
        } catch(FavError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
