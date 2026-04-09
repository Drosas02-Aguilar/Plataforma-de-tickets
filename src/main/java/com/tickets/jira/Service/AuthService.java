package com.tickets.jira.Service;

import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.DTO.LoginRequestDTO;
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
    
    public String login(String username, String password) {

        Optional<Usuario> opt = iUsuario.findByUsername(username);

        if (opt.isPresent()) {

            Usuario usuario = opt.get();

            if (passwordEncoder.matches(password, usuario.getPassword())) {

                List<String> roles = usuario.getRoles()
                        .stream()
                        .map(rol -> rol.getNombre())
                        .collect(Collectors.toList());

                return jwtUtil.GenerarToken(usuario.getUsername(), roles);
            }
        }

        return null;
    }
}
