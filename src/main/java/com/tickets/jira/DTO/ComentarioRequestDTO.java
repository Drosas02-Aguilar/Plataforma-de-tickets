package com.tickets.jira.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioRequestDTO {

@NotNull(message = "El ticket es obligatorio")
private Integer ticketsid;

@NotBlank(message = "El mensaje es obligatorio")
private String mensaje;
}
