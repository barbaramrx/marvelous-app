package com.brb.marvelousapp.service.impl;

import com.brb.marvelousapp.exception.MarvelousException;
import com.brb.marvelousapp.model.repository.ComicRepository;
import com.brb.marvelousapp.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.brb.marvelousapp.model.entity.Comic;

@Service
public class ComicServiceImpl implements ComicService {

    private ComicRepository repository;

    @Autowired
    public ComicServiceImpl(ComicRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    @Transactional
    public Comic saveComic(Comic comic) {
        //instancia a comic e verifica se o id já existe no banco de dados
        Comic checkComic = comic;
        boolean exists = repository.existsById(checkComic.getId());

        if (!exists) {
            throw new MarvelousException("Esta comic já foi inserida");
        }

        return repository.save(comic);
    }

}