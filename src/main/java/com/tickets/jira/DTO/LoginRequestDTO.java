package com.tickets.jira.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

@NotBlank(message = "El username es obligatorio")
private String username;

@NotBlank(message = "la contraseña es obligatoria")
    private String password;
}
