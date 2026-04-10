package com.tickets.jira.Service;

import com.tickets.jira.DAO.IComentario;
import com.tickets.jira.DAO.ITicket;
import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.Entity.Comentario;
import com.tickets.jira.Entity.Ticket;
import com.tickets.jira.Entity.Usuario;
import java.time.LocalDateTime;
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

    @Transactional
    public Comentario agregarComentario(Integer ticketid, Integer autorid, String mensaje) {

        Optional<Ticket> ticketOpt = iTicket.findById(ticketid);
        Optional<Usuario> usuOpt = iUsuario.findById(autorid);

        if (ticketOpt.isPresent() && usuOpt.isPresent()) {
            Comentario comentario = new Comentario();
            comentario.setTicket(ticketOpt.get());
            comentario.setAutor(usuOpt.get());
            comentario.setMensaje(mensaje);
            comentario.setFechacreacion(LocalDateTime.now());

            return iComentario.save(comentario);
        }

        return null;
    }

}
