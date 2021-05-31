package com.brb.marvelousapp.service;

import com.brb.marvelousapp.model.entity.Char;

public interface CharService {

    Char saveChar(Char character);

    Char checkIfFav(Long id);

}
