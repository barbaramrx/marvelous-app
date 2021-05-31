package com.brb.marvelousapp.service.impl;

import com.brb.marvelousapp.exception.FavError;
import com.brb.marvelousapp.exception.MarvelousException;
import com.brb.marvelousapp.model.entity.Comic;
import com.brb.marvelousapp.model.repository.CharRepository;
import com.brb.marvelousapp.service.CharService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brb.marvelousapp.model.entity.Char;

import java.util.Optional;

@Service
public class CharServiceImpl implements CharService {

    private CharRepository repository;

    @Autowired
    public CharServiceImpl(CharRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public Char saveChar(Char character) {
        //instancia a char e verifica se o id já existe no banco de dados
        Char checkChar = character;
        boolean exists = repository.existsById(checkChar.getId());

        if (exists) {
            throw new MarvelousException("Este char já foi inserido");
        }

        return repository.save(character);
    }

    @Override
    public Char checkIfExists(Char character) {
        boolean exists = repository.existsById(character.getId());

        if (!exists) {
            return repository.save(character);
        } else {
            return character;
        }
    }

    @Override
    public Char getCharById(Long id) {

        boolean exists = repository.existsById(id);

        if (exists) {
            Char character = repository.getById(id);
            return character;
        } else {
            throw new MarvelousException("O character não existe.");
        }
    }

}