package com.tickets.jira.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NOTIFICACIONES")
@Getter
@Setter
@NoArgsConstructor
public class Notificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacionid")
    private int notificacionid;

    @Column(name = "usuarioid", nullable = false)
    private Integer usuarioid;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "mensaje", nullable = false, length = 500)
    private String mensaje;

    @Column(name = "referenciaid")
    private int referenciaid;

    @Column(name = "leida", nullable = false)
    private int leida;

    @Column(name = "fechacreacion")
    private LocalDateTime fechacreacion;

    @Column(name = "fechalectura")
    private LocalDateTime fechalectura;

}
