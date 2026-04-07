package com.tickets.jira.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name="USUARIOS")
public class Usuario {
    
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idusuario")
private String idusuario;

@Column(name="nombre")
private String nombre;

@Column(name="apellidopaterno")
private String apellidopaterno;

@Column(name="apellidomaterno")
private String apellidomaterno;

@Column(name="correo", unique = true)
private String correo;

@Column(name="username", unique = true)
private String username;

@Column(name="password")
private String password;

@Column(name="activo")
private Boolean activo;

@Column(name="fechadecreacion")
private LocalDateTime fechadecreacion;

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
name="USUARIOROLES",
        joinColumns = @JoinColumn(name="idusuarios"),
        inverseJoinColumns = @JoinColumn(name="idroles")
        
)
private Set<Rol> roles;

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechadecreacion() {
        return fechadecreacion;
    }

    public void setFechadecreacion(LocalDateTime fechadecreacion) {
        this.fechadecreacion = fechadecreacion;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }





}
