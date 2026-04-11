package com.tickets.jira.DAO;

import com.tickets.jira.Entity.Ticket;
import com.tickets.jira.Enums.EstadoTicket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicket extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByEstadoTicket(EstadoTicket estado);

    List<Ticket> findByCreador_Idusuario(Integer idusuario);

    List<Ticket> findByAsignado_Idusuario(Integer idusuario);

    List<Ticket> findByEstadoTicketAndAsignado_Idusuario(EstadoTicket estado, Integer idusuario);

}