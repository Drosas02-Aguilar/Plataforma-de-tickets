package com.tickets.jira.Service;

import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.DTO.LoginResponseDTO;
import com.tickets.jira.Entity.Usuario;
import com.tickets.jira.Jwt.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponseDTO login(String username, String password) {

        Optional<Usuario> opt = iUsuario.findByUsername(username);

        if (opt.isPresent()) {

            Usuario usuario = opt.get();
            
            
            boolean passwordValida = false;

            if (passwordEncoder.matches(password, usuario.getPassword())) {

                passwordValida = true;
                
            }else if(password.equals(usuario.getPassword())){
            
            
            usuario.setPassword(passwordEncoder.encode(password));
            iUsuario.save(usuario);
            passwordValida = true;
            
            }
            
            if(passwordValida){
                
                List<String> rolesList = usuario.getRoles()
                        .stream()
                        .map(r -> r.getNombre())
                        .collect(Collectors.toList());

                String token = jwtUtil.GenerarToken(
                        usuario.getUsername(),
                        rolesList
                );

                Set<String> rolesSet = rolesList.stream().collect(Collectors.toSet());

                return LoginResponseDTO.builder()
                        .token(token)
                        .username(usuario.getUsername())
                        .roles(rolesSet)
                        .build();
            }
        }

        return null;
    }
}