package com.tickets.jira.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String apellidopaterno;
    private String apellidomaterno;
    
    @Email(message = "Correo invalido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    
    @NotBlank(message = "El username es obligatorio")
     private String username;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
    private Set<Integer> rolesids;
    }
