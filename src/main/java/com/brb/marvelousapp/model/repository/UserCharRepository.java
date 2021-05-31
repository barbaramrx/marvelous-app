package com.brb.marvelousapp.model.repository;

import com.brb.marvelousapp.model.entity.Char;
import com.brb.marvelousapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCharRepository extends JpaRepository<User, Integer> {

    boolean existsUsersByUserIdAndFavChars(Integer id, Char character);

}
