package com.mycompany.actividad1.model;

public class Programa {
    private Double id;
    private String nombre;
    private Integer duracion; 
    private java.sql.Date registro; 
    private Facultad facultad;

    public Programa() {}
    
    public Programa(Double id, String nombre, Integer duracion, java.sql.Date registro, Facultad facultad) {
    this.id = id;
    this.nombre = nombre;
    this.duracion = duracion;
    this.registro = registro;
    this.facultad = facultad;
}


    public Double getId() { return id; }
    public void setId(Double id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public java.sql.Date getRegistro() { return registro; }
    public void setRegistro(java.sql.Date registro) { this.registro = registro; }

    public Facultad getFacultad() { return facultad; }
    public void setFacultad(Facultad facultad) { this.facultad = facultad; }
}
