package com.prohire.model;

public class Vacante {
    private int id_vacante;
    private int id_empresa;
    private String cargo;
    private String salario;
    private String modalidad;
    private String tipo_contrato;
    private String ubicacion;
    private String descripcion;

    public Vacante() {}

    // Getters y Setters reales (sin el error de NetBeans)
    public int getId_vacante() { return id_vacante; }
    public void setId_vacante(int id_vacante) { this.id_vacante = id_vacante; }

    public int getId_empresa() { return id_empresa; }
    public void setId_empresa(int id_empresa) { this.id_empresa = id_empresa; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getSalario() { return salario; }
    public void setSalario(String salario) { this.salario = salario; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getTipo_contrato() { return tipo_contrato; }
    public void setTipo_contrato(String tipo_contrato) { this.tipo_contrato = tipo_contrato; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}