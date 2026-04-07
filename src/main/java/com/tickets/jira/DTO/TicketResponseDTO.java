package com.tickets.jira.DTO;

import com.tickets.jira.Enums.EstadoTicket;
import com.tickets.jira.Enums.Prioridad;
import java.time.LocalDateTime;

public class TicketResponseDTO {

private Integer id;
private String titulo;
private String descripcion;
private Prioridad prioridad;
private EstadoTicket estadoticket;

private LocalDateTime fechacreacion;

private String creador;
private String asignado;
    
}
