package com.prohire.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
    
    private int id_usuario;
    private String nombre;
    private String email;
    private String password;
    private String rol;
    private String profesion;
    private String telefono;
    
    @JsonProperty("pin_seguridad")
    private String pin_seguridad;
    
    @JsonProperty("foto_perfil")
    private String foto_perfil;
    
    @JsonProperty("cv_documento")
    private String cv_documento;

    @JsonProperty("ruta_cv")
    private String ruta_cv;

    // Constructor vacío
    public Usuario() {
    }

    // --- GETTERS Y SETTERS ---

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPin_seguridad() {
        return pin_seguridad;
    }

    public void setPin_seguridad(String pin_seguridad) {
        this.pin_seguridad = pin_seguridad;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public String getCv_documento() {
        return cv_documento;
    }

    public void setCv_documento(String cv_documento) {
        this.cv_documento = cv_documento;
    }

    public String getRuta_cv() {
        return ruta_cv;
    }

    public void setRuta_cv(String ruta_cv) {
        this.ruta_cv = ruta_cv;
    }
}