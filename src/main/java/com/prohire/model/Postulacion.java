package com.prohire.model;

public class Postulacion {
    private int id_postulacion;
    private int id_profesional;
    private int id_vacante;
    private String estado; // 'ENVIADA', 'ACEPTADO', 'RECHAZADO'
    private String fecha_postulacion;

    // Nuevos atributos para recibir la información del profesional mediante JOIN en el DAO
    private String nombreProfesional;
    private String profesionProfesional;
    private String telefonoProfesional;
    private String emailProfesional;
    private String cvProfesional;

    public Postulacion() {}

    // --- Getters y Setters Básicos ---
    public int getId_postulacion() { return id_postulacion; }
    public void setId_postulacion(int id_postulacion) { this.id_postulacion = id_postulacion; }

    public int getId_profesional() { return id_profesional; }
    public void setId_profesional(int id_profesional) { this.id_profesional = id_profesional; }

    public int getId_vacante() { return id_vacante; }
    public void setId_vacante(int id_vacante) { this.id_vacante = id_vacante; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFecha_postulacion() { return fecha_postulacion; }
    public void setFecha_postulacion(String fecha_postulacion) { this.fecha_postulacion = fecha_postulacion; }

    // --- Getters y Setters para los datos del Profesional (JOIN) ---
    public String getNombreProfesional() { return nombreProfesional; }
    public void setNombreProfesional(String nombreProfesional) { this.nombreProfesional = nombreProfesional; }

    public String getProfesionProfesional() { return profesionProfesional; }
    public void setProfesionProfesional(String profesionProfesional) { this.profesionProfesional = profesionProfesional; }

    public String getTelefonoProfesional() { return telefonoProfesional; }
    public void setTelefonoProfesional(String telefonoProfesional) { this.telefonoProfesional = telefonoProfesional; }

    public String getEmailProfesional() { return emailProfesional; }
    public void setEmailProfesional(String emailProfesional) { this.emailProfesional = emailProfesional; }

    public String getCvProfesional() { return cvProfesional; }
    public void setCvProfesional(String cvProfesional) { this.cvProfesional = cvProfesional; }
}