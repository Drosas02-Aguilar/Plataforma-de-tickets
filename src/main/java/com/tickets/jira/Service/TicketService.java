package com.tickets.jira.Service;

import com.tickets.jira.DAO.ITicket;
import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.Entity.Ticket;
import com.tickets.jira.Entity.Usuario;
import com.tickets.jira.Enums.EstadoTicket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketService {

    @Autowired
    private ITicket iTicket;

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private NotificacionService notificacionService;

    public List<Ticket> obtenerPorCreador(Integer idusuario) {
        return iTicket.findByCreador_Idusuario(idusuario);
    }

    public List<Ticket> listarTickets() {
        return iTicket.findAll();
    }

    public Ticket ObtenerPorId(Integer idticket) {
        Optional<Ticket> opt = iTicket.findById(idticket);
        return opt.orElse(null);
    }

    @Transactional
    public Ticket crearTicket(Ticket ticket, String username) {
        Optional<Usuario> usuarioOpt = iUsuario.findByUsername(username);

        if (usuarioOpt.isPresent()) {
            ticket.setCreador(usuarioOpt.get());
            ticket.setEstadoTicket(EstadoTicket.ABIERTO);
            ticket.setFechacreacion(LocalDateTime.now());

            Ticket nuevo = iTicket.save(ticket);

            notificacionService.Crear(
                    usuarioOpt.get().getIdusuario(),
                    "TICKET",
                    "Creaste el ticket #" + nuevo.getIdticket() + nuevo.getTitulo(),
                    nuevo.getIdticket()
            );

            return nuevo;
        }
        return null;
    }

    @Transactional
    public Ticket asignarTicket(Integer ticketid, Integer agenteid) {
        Optional<Ticket> ticketOpt = iTicket.findById(ticketid);
        Optional<Usuario> agenteOpt = iUsuario.findById(agenteid);

        if (ticketOpt.isPresent() && agenteOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setAsignado(agenteOpt.get());
            ticket.setEstadoTicket(EstadoTicket.PROCESO);

            iTicket.save(ticket);

            notificacionService.Crear(
                    agenteOpt.get().getIdusuario(),
                    "TICKET",
                    "Se te asignó: " + ticket.getTitulo(),
                    ticket.getIdticket()
            );

            return ticket;
        }
        return null;
    }

    @Transactional
    public Ticket cambiarEstado(Integer ticketid, EstadoTicket estado) {
        Optional<Ticket> opt = iTicket.findById(ticketid);

        if (opt.isPresent()) {
            Ticket ticket = opt.get();
            ticket.setEstadoTicket(estado);
            ticket.setFechaactualizacion(LocalDateTime.now());

            iTicket.save(ticket);

            if (ticket.getCreador() != null) {
                notificacionService.Crear(
                        ticket.getCreador().getIdusuario(),
                        "CAMBIO_ESTADO",
                        "El ticket '" + ticket.getTitulo() + "' cambió a " + estado,
                        ticket.getIdticket()
                );
            }

            return ticket;
        }
        return null;
    }
}
