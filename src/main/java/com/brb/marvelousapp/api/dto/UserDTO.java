package com.brb.marvelousapp.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String email;
    private String name;
    private String password;
    private String profile;

}
