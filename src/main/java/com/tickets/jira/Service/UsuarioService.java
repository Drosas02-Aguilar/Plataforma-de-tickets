package com.tickets.jira.Service;

import com.tickets.jira.DAO.IRoles;
import com.tickets.jira.DAO.IUsuario;
import com.tickets.jira.Entity.Rol;
import com.tickets.jira.Entity.Usuario;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuario iUsuario;

    @Autowired
    private IRoles iRoles;

    public List<Usuario> listarUsuarios() {
        return iUsuario.findAll();
    }

    public Usuario obtenerPorId(Integer idusuario) {
        Optional<Usuario> opt = iUsuario.findById(idusuario);
        return opt.orElse(null);
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario, Set<Integer> rolesids) {

        usuario.setActivo(true);
        usuario.setFechadecreacion(LocalDateTime.now());

        Set<Rol> roles = new HashSet<>();
        for (Integer idRol : rolesids) {
            iRoles.findById(idRol).ifPresent(roles::add);
        }

        usuario.setRoles(roles);
        return iUsuario.save(usuario);

    }

    @Transactional
    public Usuario actualizarUsuario(Integer idusuario, Usuario usuarioNuevo) {

        Optional<Usuario> opt = iUsuario.findById(idusuario);

        if (opt.isPresent()) {

            Usuario usuario = opt.get();

            usuario.setNombre(usuarioNuevo.getNombre());
            usuario.setApellidopaterno(usuarioNuevo.getApellidopaterno());
            usuario.setApellidomaterno(usuarioNuevo.getApellidomaterno());
            usuario.setCorreo(usuarioNuevo.getCorreo());

            iUsuario.save(usuario);
            return usuario;

        }
        return null;
    }

}
