package com.brb.marvelousapp.model.repository;

import com.brb.marvelousapp.model.entity.Comic;
import com.brb.marvelousapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserComicRepository extends JpaRepository<User, Integer> {

    boolean existsUsersByUserIdAndFavComics(Integer id, Comic comic);
}
