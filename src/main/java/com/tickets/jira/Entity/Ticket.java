package com.tickets.jira.Entity;

import com.tickets.jira.Enums.EstadoTicket;
import com.tickets.jira.Enums.Prioridad;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TICKETS")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idticket")
    private int idticket;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoTicket estadoTicket;

    @Column(name = "fechacreacion")
    private LocalDateTime fechacreacion;

    @Column(name = "fechaactualizacion")
    private LocalDateTime fechaactualizacion;

    @ManyToOne
    @JoinColumn(name = "creadoid")
    private Usuario creador;

    @ManyToOne
    @JoinColumn(name = "asignadoid")
    private Usuario asignado;

}
