package com.tickets.jira.RestController;

import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.DTO.UsuarioRequestDTO;
import com.tickets.jira.DTO.UsuarioResponseDTO;
import com.tickets.jira.Entity.Usuario;
import com.tickets.jira.Exception.ServiceResult;
import com.tickets.jira.Service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN','USER','AGENTE')")
    @GetMapping
    public ResponseEntity<ServiceResult<UsuarioResponseDTO>> listarUsuarios() {

        ServiceResult<UsuarioResponseDTO> result = new ServiceResult<>();

        try {

            List<Usuario> usuarios = usuarioService.listarUsuarios();

            if (!usuarios.isEmpty()) {

                List<UsuarioResponseDTO> lista = usuarios.stream().map(u -> {
                    UsuarioResponseDTO dto = new UsuarioResponseDTO();
                    dto.setId(u.getIdusuario());
                    dto.setNombre(u.getNombre());
                    dto.setApellidopaterno(u.getApellidopaterno());
                    dto.setApellidomaterno(u.getApellidomaterno());
                    dto.setCorreo(u.getCorreo());
                    dto.setUsername(u.getUsername());
                    dto.setActivo(u.getActivo());

                    dto.setRoles(
                            u.getRoles().stream()
                                    .map(r -> r.getNombre())
                                    .collect(Collectors.toSet())
                    );

                    return dto;
                }).collect(Collectors.toList());

                result.Objects = lista;
                result.correct = true;
                result.status = 200;
                result.message = "Usuarios encontrados";

            } else {
                result.status = 404;
                result.ErrorMessage = "No hay usuarios";
            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','AGENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResult<UsuarioResponseDTO>> ObtenerPorId(
            @PathVariable Integer id) {

        ServiceResult<UsuarioResponseDTO> result = new ServiceResult<>();

        try {

            Usuario usu = usuarioService.obtenerPorId(id);

            if (usu != null) {
                UsuarioResponseDTO dto = new UsuarioResponseDTO();
                dto.setId(usu.getIdusuario());
                dto.setNombre(usu.getNombre());
                dto.setApellidopaterno(usu.getApellidopaterno());
                dto.setApellidomaterno(usu.getApellidomaterno());
                dto.setCorreo(usu.getCorreo());
                dto.setUsername(usu.getUsername());
                dto.setActivo(usu.getActivo());

                result.object = dto;
                result.correct = true;
                result.status = 200;
                result.message = "Usuario encontrado";
            } else {
                result.status = 404;
                result.ErrorMessage = "Usuario no encontrado con el id: " + id;
            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','AGENTE')")
    @PostMapping
    public ResponseEntity<ServiceResult<UsuarioResponseDTO>> CrearUsuario(
            @RequestBody UsuarioRequestDTO request) {

        ServiceResult<UsuarioResponseDTO> result = new ServiceResult<>();

        try {

            Usuario usuario = new Usuario();

            usuario.setNombre(request.getNombre());
            usuario.setApellidopaterno(request.getApellidopaterno());
            usuario.setApellidomaterno(request.getApellidomaterno());
            usuario.setCorreo(request.getCorreo());
            usuario.setUsername(request.getUsername());
            usuario.setPassword(request.getPassword());

            Usuario creado = usuarioService.crearUsuario(usuario, request.getRolesids());

            if (creado != null) {
                UsuarioResponseDTO dto = new UsuarioResponseDTO();
                dto.setNombre(creado.getNombre());
                dto.setApellidopaterno(creado.getApellidopaterno());
                dto.setApellidomaterno(creado.getApellidomaterno());
                dto.setCorreo(creado.getCorreo());
                dto.setUsername(creado.getUsername());
                dto.setActivo(creado.getActivo());
                dto.setRoles(
                        creado.getRoles().stream()
                                .map(r -> r.getNombre())
                                .collect(Collectors.toSet())
                );
                dto.setFechadecreacion(creado.getFechadecreacion());

                result.object = dto;
                result.correct = true;
                result.status = 201;
                result.message = "Usuario creado correctamente";
            } else {

                result.status = 400;
                result.ErrorMessage = "No se pudo crear el usuario";

            }

        } catch (Exception ex) {

            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();

        }
        return ResponseEntity.status(result.status).body(result);

    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','AGENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult<UsuarioResponseDTO>> actualizarUsuario(
            @PathVariable Integer id, @RequestBody Usuario request
    ) {
        ServiceResult<UsuarioResponseDTO> result = new ServiceResult<>();
        try {
            Usuario actualizado = usuarioService.actualizarUsuario(id, request);
            if (actualizado != null) {

                UsuarioResponseDTO dto = new UsuarioResponseDTO();
                dto.setId(actualizado.getIdusuario());
                dto.setNombre(actualizado.getNombre());
                dto.setApellidopaterno(actualizado.getApellidopaterno());
                dto.setApellidomaterno(actualizado.getApellidomaterno());
                dto.setCorreo(actualizado.getCorreo());
                dto.setUsername(actualizado.getUsername());
                result.object = dto;
                result.correct = true;
                result.status = 200;
                result.message = "Usuario actualizado correctamente";

            } else {
                result.status = 404;
                result.ErrorMessage = "Usuario no encontrado con id: " + id;
            }
        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AGENTE')")
    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<ServiceResult<UsuarioResponseDTO>> desactivarUsuario(
            @PathVariable Integer id
    ) {
        ServiceResult<UsuarioResponseDTO> result = new ServiceResult<>();

        try {

            Usuario desactivado = usuarioService.desactivarUsuario(id);

            if (desactivado != null) {

                UsuarioResponseDTO dto = new UsuarioResponseDTO();
                dto.setId(desactivado.getIdusuario());
                dto.setNombre(desactivado.getNombre());
                dto.setApellidopaterno(desactivado.getApellidopaterno());
                dto.setApellidomaterno(desactivado.getApellidomaterno());
                dto.setCorreo(desactivado.getCorreo());
                dto.setUsername(desactivado.getUsername());
                dto.setActivo(desactivado.getActivo());
                dto.setRoles(
                        desactivado.getRoles().stream()
                                .map(r -> r.getNombre())
                                .collect(Collectors.toSet())
                );

                result.object = dto;
                result.correct = true;
                result.status = 200;
                result.message = "Usuario desactivado";

            } else {
                result.status = 404;
                result.ErrorMessage = "Usuario no encontrado";

            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }

        return ResponseEntity.status(result.status).body(result);

    }

    @PreAuthorize("hasAnyRole('ADMIN','AGENTE')")
    @PatchMapping("/activar/{id}")
    public ResponseEntity<ServiceResult<UsuarioResponseDTO>> activarUsuario(
            @PathVariable Integer id
    ) {
        ServiceResult<UsuarioResponseDTO> result = new ServiceResult<>();

        Usuario activado = usuarioService.activarUsuario(id);

        if (activado != null) {

            UsuarioResponseDTO dto = new UsuarioResponseDTO();

            dto.setId(activado.getIdusuario());
            dto.setNombre(activado.getNombre());
            dto.setApellidopaterno(activado.getApellidopaterno());
            dto.setApellidomaterno(activado.getApellidomaterno());
            dto.setCorreo(activado.getCorreo());
            dto.setUsername(activado.getUsername());
            dto.setActivo(activado.getActivo());
            dto.setRoles(
                    activado.getRoles().stream()
                            .map(r -> r.getNombre())
                            .collect(Collectors.toSet())
            );
            
            result.object = dto;
            result.correct = true;
            result.status = 200;
            result.message = "Usuario activado";

        } else {
            result.status = 404;
            result.ErrorMessage = "No se encontro el usuario con el id: " + id;
        }

        try {
        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }
        return ResponseEntity.status(result.status).body(result);
    }

}
