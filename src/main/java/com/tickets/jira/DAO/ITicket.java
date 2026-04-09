package com.tickets.jira.DAO;

import com.tickets.jira.Entity.Ticket;
import com.tickets.jira.Enums.EstadoTicket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicket  extends JpaRepository<Ticket, Integer>{
    
    List<Ticket> findByEstado(EstadoTicket estado);
    List<Ticket> findByCreador_IdUsuario(Integer idusuario);
    List<Ticket> findByAsignado_IdUsuario(Integer idusuario);
    List<Ticket> findByEsradoAndAsignado_IdUsuario(EstadoTicket estado, Integer idusuario);
    
    
    
}
