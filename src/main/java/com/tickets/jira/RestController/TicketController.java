package com.tickets.jira.RestController;

import com.tickets.jira.DTO.TicketRequestDTO;
import com.tickets.jira.DTO.TicketResponseDTO;
import com.tickets.jira.Entity.Ticket;
import com.tickets.jira.Exception.ServiceResult;
import com.tickets.jira.Service.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService tieckService;

    @GetMapping
    public ResponseEntity<ServiceResult<TicketResponseDTO>> listarTickets() {

        ServiceResult<TicketResponseDTO> result = new ServiceResult<>();

        try {

            List<Ticket> lista = tieckService.listarTickets();

            if (!lista.isEmpty()) {

                List<TicketResponseDTO> dtos = lista.stream().map(t -> {
                    TicketResponseDTO dto = new TicketResponseDTO();

                    dto.setId(t.getIdticket());
                    dto.setTitulo(t.getTitulo());
                    dto.setDescripcion(t.getDescripcion());
                    dto.setPrioridad(t.getPrioridad());
                    dto.setEstadoticket(t.getEstadoTicket());
                    dto.setFechacreacion(t.getFechacreacion());

                    dto.setCreador(
                            t.getCreador() != null ? t.getCreador().getUsername() : null
                    );

                    dto.setAsignado(
                            t.getAsignado() != null ? t.getAsignado().getUsername() : null
                    );

                    return dto;
                }).toList();

                result.Objects = dtos;
                result.correct = true;
                result.status = 200;
                result.message = "Tickets encontrados";
            } else {
                result.status = 404;
                result.ErrorMessage = "No hay tickets";

            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<TicketResponseDTO>> obternerPorId(
            @PathVariable Integer id) {

        ServiceResult<TicketResponseDTO> result = new ServiceResult<>();
        try {
            Ticket tik = tieckService.ObtenerPorId(id);

            if (tik != null) {

                TicketResponseDTO dto = new TicketResponseDTO();

                dto.setId(tik.getIdticket());
                dto.setTitulo(tik.getTitulo());
                dto.setDescripcion(tik.getDescripcion());
                dto.setPrioridad(tik.getPrioridad());
                dto.setEstadoticket(tik.getEstadoTicket());
                dto.setFechacreacion(tik.getFechacreacion());

                dto.setCreador(
                        tik.getCreador() != null ? tik.getCreador().getUsername() : null
                );

                dto.setAsignado(
                        tik.getAsignado() != null ? tik.getAsignado().getUsername() : null
                );

                result.Object = dto;
                result.correct = true;
                result.status = 200;
                result.message = "Ticket encontrado";

            } else {

                result.status = 404;
                result.ErrorMessage = " Ticket no encontrado con id: " + id;
            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping
    public ResponseEntity<ServiceResult<TicketResponseDTO>> crearTicket(
            @RequestBody TicketRequestDTO request) {

        ServiceResult result = new ServiceResult();

        try {

            Ticket ticket = new Ticket();
            ticket.setTitulo(request.getTitulo());
            ticket.setDescripcion(request.getDescripcion());
            ticket.setPrioridad(request.getPrioridad());

            Ticket creado = tieckService.crearTicket(ticket, request.getCreadorid());

            if (creado != null) {

                TicketResponseDTO dto = new TicketResponseDTO();

                dto.setId(creado.getIdticket());
                dto.setTitulo(creado.getTitulo());
                dto.setDescripcion(creado.getDescripcion());
                dto.setPrioridad(creado.getPrioridad());
                dto.setEstadoticket(creado.getEstadoTicket());
                dto.setFechacreacion(creado.getFechacreacion());

                dto.setCreador(
                        creado.getCreador() != null ? creado.getCreador().getUsername() : null
                );

                dto.setAsignado(
                        creado.getAsignado() != null ? creado.getAsignado().getUsername() : null
                );

                result.Object = dto;
                result.correct = true;
                result.status = 201;
                result.message = "Ticket creado correctamente";

            } else {
                result.status = 400;
                result.ErrorMessage = "No se pudo crear el tikect";

            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PutMapping("/{id}/asignar/{agenteid}")
    public ResponseEntity<ServiceResult<TicketResponseDTO>> asignarTicket(
            @PathVariable Integer id,
            @PathVariable Integer agenteid) {

        ServiceResult<TicketResponseDTO> result = new ServiceResult<>();

        try {
            
            
        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

}
