package com.brb.marvelousapp.model.repository;

import com.brb.marvelousapp.model.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicRepository extends JpaRepository<Comic, Long> {
}
