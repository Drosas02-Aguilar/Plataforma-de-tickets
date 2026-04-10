package com.tickets.jira.DAO;

import com.tickets.jira.Entity.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComentario extends JpaRepository<Comentario, Integer> {
    
    
    List<Comentario> findByTicket_Id(Integer ticketId);

    List<Comentario> findByAutor_IdUsuario(Integer autorId);    
    
}
