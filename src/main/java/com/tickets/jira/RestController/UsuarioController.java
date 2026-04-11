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

    @PreAuthorize("hasRole('ADMIN','USUARIO','GERENTE')")
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

    @PreAuthorize("hasRole('ADMIN','USUARIO','GERENTE')")
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

    @PreAuthorize("hasRole('ADMIN','USUARIO','GERENTE')")
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
                dto.setId(creado.getIdusuario());
                dto.setNombre(creado.getNombre());

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

    @PreAuthorize("hasRole('ADMIN','USUARIO','GERENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResult<Usuario>> actualizarUsuario(
            @PathVariable Integer id, @RequestBody Usuario request
    ) {

        ServiceResult<Usuario> result = new ServiceResult<>();

        try {

            Usuario actualizado = usuarioService.actualizarUsuario(id, request);

            if (actualizado != null) {

                result.object = actualizado;
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

    @PreAuthorize("hasRole('ADMIN','GERENTE)")
    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<ServiceResult<Usuario>> desactivarUsuario(
            @PathVariable Integer id
    ) {
        ServiceResult result = new ServiceResult();

        try {

            Usuario usuario = usuarioService.desactivarUsuario(id);

            if (usuario != null) {

                result.object = usuario;
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

    @PreAuthorize("hasRole('ADMIN','GERENTE)")
    @PatchMapping("/activar/{id}")
    public ResponseEntity<ServiceResult<Usuario>> activarUsuario(
            @PathVariable Integer id
    ) {
        ServiceResult result = new ServiceResult();

        Usuario usuario = usuarioService.activarUsuario(id);

        if (usuario != null) {

            result.object = usuario;
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
