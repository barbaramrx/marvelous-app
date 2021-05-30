package com.brb.marvelousapp.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharDTO {

    private Long id;
    private String name;
    private Long user_id;
}
