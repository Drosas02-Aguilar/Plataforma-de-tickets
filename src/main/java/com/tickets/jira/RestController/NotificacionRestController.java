package com.tickets.jira.RestController;

import com.tickets.jira.Entity.Notificaciones;
import com.tickets.jira.Exception.ServiceResult;
import com.tickets.jira.Service.NotificacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionRestController {

    @Autowired
    private NotificacionService notificacionService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/usuario/{usuarioid}")
    public ResponseEntity<ServiceResult<Notificaciones>> obtenerPorUsuario(
            @PathVariable Integer usuarioid) {

        ServiceResult<Notificaciones> result = new ServiceResult<>();

        try {
            List<Notificaciones> lista = notificacionService.ObtenerPorUsuario(usuarioid);

            result.Objects = lista;
            result.correct = true;
            result.status = 200;
            result.message = "Notificaciones obtenidas";

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pendientes/{usuarioid}")
    public ResponseEntity<ServiceResult<Integer>> contarPendientes(
            @PathVariable Integer usuarioid) {

        ServiceResult<Integer> result = new ServiceResult<>();

        try {
            int total = notificacionService.ContarPendientes(usuarioid);

            result.object = total;
            result.correct = true;
            result.status = 200;
            result.message = "Pendientes: " + total;

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/leer/{notificacionid}")
    public ResponseEntity<ServiceResult<String>> marcarComoLeida(
            @PathVariable Integer notificacionid) {

        ServiceResult<String> result = new ServiceResult<>();

        try {
            Notificaciones notif = notificacionService.MarcarCOmoleida(notificacionid);

            if (notif != null) {
                result.correct = true;
                result.status = 200;
                result.message = "Notificación marcada como leída";
            } else {
                result.status = 404;
                result.ErrorMessage = "No encontrada";
            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/leer-todas/{usuarioid}")
    public ResponseEntity<ServiceResult<String>> marcarTodasComoLeidas(
            @PathVariable Integer usuarioid) {

        ServiceResult<String> result = new ServiceResult<>();

        try {
            int total = notificacionService.MarcarTodasComoLeidas(usuarioid);

            result.correct = true;
            result.status = 200;
            result.message = total + " notificaciones marcadas como leídas";

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ServiceResult<Notificaciones>> crear(
            @RequestBody Notificaciones notificacion) {

        ServiceResult<Notificaciones> result = new ServiceResult<>();

        try {
            Notificaciones creada = notificacionService.Crear(
                    notificacion.getUsuarioid(),
                    notificacion.getTipo(),
                    notificacion.getMensaje(),
                    notificacion.getReferenciaid()
            );

            result.object = creada;
            result.correct = true;
            result.status = 201;
            result.message = "Notificación creada correctamente";

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }
}