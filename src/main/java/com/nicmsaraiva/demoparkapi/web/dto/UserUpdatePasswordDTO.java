package com.nicmsaraiva.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserUpdatePasswordDTO {
    @NotBlank
    @Size(min = 6, max = 6, message = "Invalid current password length")
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 6, message = "Invalid new password length")
    private String newPassword;
    @NotBlank
    @Size(min = 6, max = 6, message = "Invalid confirm password length")
    private String confirmPassword;
}
