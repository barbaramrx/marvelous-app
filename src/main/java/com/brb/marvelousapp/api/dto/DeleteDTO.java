package com.brb.marvelousapp.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDTO {

    private Long id;
    private String name;
    private Integer idUser;

}
