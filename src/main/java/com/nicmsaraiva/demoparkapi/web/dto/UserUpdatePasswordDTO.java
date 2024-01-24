package com.nicmsaraiva.demoparkapi.web.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserUpdatePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
