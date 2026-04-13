package com.tickets.jira.RestController;

import com.tickets.jira.DTO.ComentarioRequestDTO;
import com.tickets.jira.DTO.ComentarioResponseDTO;
import com.tickets.jira.Entity.Comentario;
import com.tickets.jira.Exception.ServiceResult;
import com.tickets.jira.Service.ComentarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comentarios")
public class ComentariosRestController {

    @Autowired
    private ComentarioService comentarioService;

    
        @PreAuthorize("isAuthenticated()")
    @GetMapping("/ticket/{ticketid}")
    public ResponseEntity<ServiceResult<ComentarioResponseDTO>> listarPorTicket(
            @PathVariable Integer ticketid
    ) {

        ServiceResult<ComentarioResponseDTO> result = new ServiceResult<>();

        try {

            List<Comentario> lista = comentarioService.ObtenerPorTicket(ticketid);

            if (!lista.isEmpty()) {

                List<ComentarioResponseDTO> dtos = lista.stream().map(c -> {
                    ComentarioResponseDTO dto = new ComentarioResponseDTO();

                    dto.setId(c.getIdcomentario());
                    dto.setMensaje(c.getMensaje());
                    dto.setFechacreacion(c.getFechacreacion());

                    dto.setAutor(
                            c.getAutor() != null
                            ? c.getAutor().getUsername()
                            : null
                    );

                    return dto;
                }).toList();
                
                result.Objects = dtos;
                result.correct = true;
                result.status = 200;
                result.message = "Comentarios encontrados";

            }else{
                result.status = 500;
                result.ErrorMessage = "No hay comentarios en este Ticket";
            
            }

        } catch (Exception ex) {

            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();

        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ServiceResult<ComentarioResponseDTO>> agregarComentario(
            @RequestBody ComentarioRequestDTO request, @AuthenticationPrincipal String username
    ) {
        ServiceResult<ComentarioResponseDTO> result = new ServiceResult<>();
        try {

            Comentario comentario = comentarioService.agregarComentario(
                    request.getTicketsid(),
                    username,
                    request.getMensaje()
            );

            if (comentario != null) {
                ComentarioResponseDTO dto = new ComentarioResponseDTO();

                dto.setId(comentario.getIdcomentario());
                dto.setMensaje(comentario.getMensaje());
                dto.setFechacreacion(comentario.getFechacreacion());

                dto.setAutor(
                        comentario.getAutor() != null
                        ? comentario.getAutor().getUsername() : null
                );

                result.object = dto;
                result.correct = true;
                result.status = 201;
                result.message = "Comentario agregado correctamente";

            } else {
                result.status = 400;
                result.ErrorMessage = " No se pudo agregar el mensaje";

            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

}
