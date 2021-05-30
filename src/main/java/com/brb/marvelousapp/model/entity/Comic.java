package com.brb.marvelousapp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comic", schema = "marvelous")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comic {

    @Id
    @Column(name = "comic_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "favComics")
    List<User> users;
}
