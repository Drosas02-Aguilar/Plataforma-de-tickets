package com.tickets.jira.Service;

import com.tickets.jira.DAO.IComentario;
import com.tickets.jira.DAO.ITicket;
import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.Entity.Comentario;
import com.tickets.jira.Entity.Ticket;
import com.tickets.jira.Entity.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

    @Autowired
    private IComentario iComentario;

    @Autowired
    private ITicket iTicket;

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private NotificacionService notificacionService;

    public List<Comentario> ObtenerPorTicket(Integer ticketid) {
        return iComentario.findByTicket_Idticket(ticketid);
    }

    @Transactional
    public Comentario agregarComentario(Integer ticketid, String username, String mensaje) {

        Optional<Ticket> ticketOpt = iTicket.findById(ticketid);
        Optional<Usuario> usuOpt = iUsuario.findByUsername(username);

        if (ticketOpt.isPresent() && usuOpt.isPresent()) {

            Ticket ticket = ticketOpt.get();
            Usuario usuario = usuOpt.get();

            Comentario comentario = new Comentario();
            comentario.setTicket(ticket);
            comentario.setAutor(usuario);
            comentario.setMensaje(mensaje);
            comentario.setFechacreacion(LocalDateTime.now());

            Comentario guardado = iComentario.save(comentario);

            if (ticket.getCreador() != null
                    && ticket.getCreador().getIdusuario() != usuario.getIdusuario()) {

                notificacionService.Crear(
                        ticket.getCreador().getIdusuario(),
                        "COMENTARIO",
                        "Nuevo comentario en el ticket #" + ticket.getTitulo(),
                        ticket.getIdticket()
                );
            }

            return guardado;
        }

        return null;
    }
}
