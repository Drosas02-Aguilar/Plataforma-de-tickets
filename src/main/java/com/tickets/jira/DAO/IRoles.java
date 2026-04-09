package com.tickets.jira.DAO;

import com.tickets.jira.Entity.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoles extends JpaRepository<Rol, Integer> {
    
    
    Optional<Rol> findByNombre(String nombre);
    
}
