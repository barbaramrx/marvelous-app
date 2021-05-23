package com.brb.marvelousapp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "char", schema = "marvelous")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Char {

    @Id
    @Column(name = "char_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long char_id;

    @Column(name = "name")
    private String name;

    @Column(name = "bio")
    private String bio;

    @ManyToMany(mappedBy = "favChars")
    Set<User> users;
}
