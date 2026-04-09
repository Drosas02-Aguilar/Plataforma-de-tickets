package com.tickets.jira.DAO;

import com.tickets.jira.Entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuario extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByActivo(Boolean activo);

    List<Usuario> findByRoles_Nombre(String nombreRol);
}
