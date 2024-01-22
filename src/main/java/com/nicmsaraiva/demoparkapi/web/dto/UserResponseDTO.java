package com.nicmsaraiva.demoparkapi.web.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class UserResponseDTO {
    private Long id;
    private String username;
    private String role;
}
