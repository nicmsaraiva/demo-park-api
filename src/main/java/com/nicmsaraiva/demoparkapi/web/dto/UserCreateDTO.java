package com.nicmsaraiva.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserCreateDTO {
    @NotBlank
    @Email(message = "Invalid email format.")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6, message = "Invalid password length")
    private String password;
}
