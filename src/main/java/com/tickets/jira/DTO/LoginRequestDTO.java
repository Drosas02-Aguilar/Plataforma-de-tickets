package com.tickets.jira.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

@NotBlank(message = "El username es obligatorio")
private String username;

@NotBlank(message = "la contraseña es obligatoria")
    private String password;
}
