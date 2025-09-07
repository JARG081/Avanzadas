package com.mycompany.actividad1.model;

public class Profesor {
    private double idPersona;   // FK a persona
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

    public double getIdPersona() { return idPersona; }
    public void setIdPersona(double idPersona) { this.idPersona = idPersona; }

    public String getContrato() { return contrato; }
    public void setContrato(String contrato) { this.contrato = contrato; }

    public Persona getPersona() { return persona; }
    public void setPersona(Persona persona) { this.persona = persona; }

    // ---- Delegados útiles ----
    public String getNombres() { return persona != null ? persona.getNombres() : ""; }
    public String getApellidos() { return persona != null ? persona.getApellidos() : ""; }

    // ---- COMPATIBILIDAD con controladores/tablas ----
    // Muchos controladores esperan p.getId(). Lo exponemos como alias del idPersona.
    public Long getId() {
        // Preferimos el ID real de Persona si está disponible:
        if (persona != null && persona.getId() != null) return persona.getId();
        // Si no hay Persona cargada, usamos el idPersona (double) como Long
        return (long) idPersona;
    }

    // Si en algún lado usan getID() (mayúscula), también lo damos:
    public Long getID() { return getId(); }
}
