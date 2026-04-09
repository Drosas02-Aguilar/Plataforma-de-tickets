package com.tickets.jira.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idusuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidopaterno")
    private String apellidopaterno;

    @Column(name = "apellidomaterno")
    private String apellidomaterno;

    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fechadecreacion")
    private LocalDateTime fechadecreacion;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USUARIOROLES",
            joinColumns = @JoinColumn(name = "idusuarios"),
            inverseJoinColumns = @JoinColumn(name = "idroles")
    )
    private Set<Rol> roles;

}
