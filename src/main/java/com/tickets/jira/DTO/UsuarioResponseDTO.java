package com.tickets.jira.DTO;

import java.util.Set;

public class UsuarioResponseDTO {

    private Integer id;
    private String nombre;
    private String apellidopaterno;
    private String apellidomaterno;
    private String correo;
    private String username;
    private Boolean activo;

    private Set<String> roles;
}
