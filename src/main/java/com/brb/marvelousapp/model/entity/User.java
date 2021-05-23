package com.brb.marvelousapp.model.entity;

import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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

    @Column(name = "account_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate accountDate;

    @ManyToMany
    @JoinTable(name = "user_comic",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id"))
    Set<Comic> favComics;

    @ManyToMany
    @JoinTable(name = "user_char",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "char_id"))
    Set<Char> favChars;
}
