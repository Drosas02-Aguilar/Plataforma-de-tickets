package com.tickets.jira.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "COMENTARIOS")
@Getter
@Setter
@NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomentario")
    private int idcomentario;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fechacreacion")
    private LocalDateTime fechacreacion;

    @ManyToOne
    @JoinColumn(name = "ticketsid")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "autorid")
    private Usuario autor;

}
