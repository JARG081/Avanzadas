package com.mycompany.actividad1.model;

public class Profesor {
    private double idPersona;   
    private String contrato; 
    private Persona persona; 
    public Profesor() {}

    public Profesor(double idPersona, String contrato, Persona persona) {
        this.idPersona = idPersona;
        this.contrato = contrato;
        this.persona = persona;
    }
    public Profesor(double idPersona, String contrato) {
    this.idPersona = idPersona;
    this.contrato = contrato;
}

    public double getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(double idPersona) {
        this.idPersona = idPersona;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    // Delegamos nombres/apellidos a Persona
    public String getNombres() {
        return persona != null ? persona.getNombres() : "";
    }

    public String getApellidos() {
        return persona != null ? persona.getApellidos() : "";
    }
}
