package com.brb.marvelousapp.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComicDTO {

    private Long id;
    private String name;
    private String description;

}
