package com.brb.marvelousapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "marvelous")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "profile")
    private String profile;

    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(name = "user_comic",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id"),
            schema = "marvelous")
    List<Comic> favComics;

    @ManyToMany
    @JoinTable(name = "user_char",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "char_id"),
            schema = "marvelous")
    List<Char> favChars;

}
