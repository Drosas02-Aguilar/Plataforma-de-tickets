package com.tickets.jira.Service;

import com.tickets.jira.DAO.INotificaciones;
import com.tickets.jira.Entity.Notificaciones;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificacionService {

    @Autowired
    private INotificaciones iNotificaciones;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<Notificaciones> ObtenerPorUsuario(Integer usuarioid) {
        return iNotificaciones.findByUsuarioidOrderByFechacreacionDesc(usuarioid);
    }

    public int ContarPendientes(Integer usuarioid) {
        return iNotificaciones.findByUsuarioidAndLeida(usuarioid, 0).size();
    }

    @Transactional
    public Notificaciones MarcarCOmoleida(Integer usuarioid) {
        Optional<Notificaciones> opt = iNotificaciones.findById(usuarioid);
        if (opt.isPresent()) {
            Notificaciones notif = opt.get();
            notif.setLeida(1);
            notif.setFechalectura(LocalDateTime.now());
            iNotificaciones.save(notif);
            return notif;
        }
        return null;
    }

    @Transactional
    public int MarcarTodasComoLeidas(Integer usuarioid) {
        List<Notificaciones> pendientes = iNotificaciones.findByUsuarioidAndLeida(usuarioid, 0);
        for (Notificaciones noti : pendientes) {
            noti.setLeida(1);
            noti.setFechalectura(LocalDateTime.now());
            iNotificaciones.save(noti);
        }
        return pendientes.size();
    }

    @Transactional
public Notificaciones Crear(Integer usuarioid, String tipo, String mensaje, Integer referenciaid) {

    Notificaciones notificaciones = new Notificaciones();

    notificaciones.setUsuarioid(usuarioid);
    notificaciones.setTipo(tipo);
    notificaciones.setMensaje(mensaje);
    notificaciones.setReferenciaid(referenciaid);
    notificaciones.setLeida(0);
    notificaciones.setFechacreacion(LocalDateTime.now());

    Notificaciones nueva = iNotificaciones.save(notificaciones);

    messagingTemplate.convertAndSend(
        "/topic/notificaciones/" + usuarioid,
        nueva
    );

    return nueva;
}

}
