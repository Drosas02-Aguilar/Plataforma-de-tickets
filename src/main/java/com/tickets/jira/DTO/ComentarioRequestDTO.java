package com.tickets.jira.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComentarioRequestDTO {

@NotNull(message = "El ticket es obligatorio")
private Integer ticketsid;

@NotNull(message = "El autor es obligatorio")
    private Integer autorid;

@NotBlank(message = "El mensaje es obligatorio")
private String mensaje;
}
