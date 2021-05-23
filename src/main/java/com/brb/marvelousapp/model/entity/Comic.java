package com.brb.marvelousapp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "comic", schema = "marvelous")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comic {

    @Id
    @Column(name = "comic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comic_id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @ManyToMany(mappedBy = "favComics")
    Set<User> users;
}
