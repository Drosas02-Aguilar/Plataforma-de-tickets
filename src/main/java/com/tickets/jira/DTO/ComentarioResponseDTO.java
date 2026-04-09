package com.tickets.jira.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioResponseDTO {

private Integer id;
private String mensaje;
private LocalDateTime fechacreacion;

private String autor;
    
}
