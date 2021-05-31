package com.brb.marvelousapp.model.repository;

import com.brb.marvelousapp.model.entity.Char;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharRepository extends JpaRepository<Char, Long> {

}
