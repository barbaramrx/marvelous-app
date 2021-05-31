package com.brb.marvelousapp.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "char", schema = "marvelous")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Char {

    @Id
    @Column(name = "char_id")
    private Long id;

    @Column(name = "name")
    private String name;

}
