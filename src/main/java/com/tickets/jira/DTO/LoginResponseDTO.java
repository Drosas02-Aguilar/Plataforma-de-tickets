package com.tickets.jira.DTO;

import java.util.Set;

public class LoginResponseDTO {

    private String token;
    private String tipo = "Bearer ";

    private String username;
    private Set<String> roles;

}
