package com.brb.marvelousapp.service;

import com.brb.marvelousapp.model.entity.Comic;

public interface ComicService {

    Comic saveComic(Comic comic);

    Comic checkIfExists(Comic comic);

    Comic getComicById(Long id);

}
