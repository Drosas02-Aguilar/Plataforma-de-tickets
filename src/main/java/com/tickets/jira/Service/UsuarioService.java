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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

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

            usuario.setNombre(tieneValor(usuarioNuevo.getNombre()) ? usuarioNuevo.getNombre() : usuario.getNombre());
            usuario.setApellidopaterno(tieneValor(usuarioNuevo.getApellidopaterno()) ? usuarioNuevo.getApellidopaterno() : usuario.getApellidopaterno());
            usuario.setApellidomaterno(tieneValor(usuarioNuevo.getApellidomaterno()) ? usuarioNuevo.getApellidomaterno() : usuario.getApellidomaterno());
            usuario.setCorreo(tieneValor(usuarioNuevo.getCorreo()) ? usuarioNuevo.getCorreo() : usuario.getCorreo());
            usuario.setPassword(tieneValor(usuarioNuevo.getPassword()) ? passwordEncoder.encode(usuarioNuevo.getPassword()) : usuario.getPassword());
            iUsuario.save(usuario);
            return usuario;

        }
        return null;
    }

    @Transactional
    public Usuario desactivarUsuario(Integer idusuario) {

        Optional<Usuario> opt = iUsuario.findById(idusuario);

        if (opt.isPresent()) {
            Usuario usuario = opt.get();
            usuario.setActivo(false);
            iUsuario.save(usuario);
            return usuario;

        }
        return null;
    }

    @Transactional
    public Usuario activarUsuario(Integer idusuario) {
        Optional<Usuario> opt = iUsuario.findById(idusuario);
        if (opt.isPresent()) {
            Usuario usuario = opt.get();
            usuario.setActivo(true);
            iUsuario.save(usuario);
            return usuario;
        }
        return null;
    }

    private boolean tieneValor(String valor) {
        return valor != null && !valor.isBlank();
    }

}
