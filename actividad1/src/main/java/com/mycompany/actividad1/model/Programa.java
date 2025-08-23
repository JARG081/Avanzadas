package com.mycompany.actividad1.model;

import java.util.Date;

public class Programa {
    private Double ID;
    private String nombre;
    private String duracion;
    private Date registro;
    private Facultad facultad;

    public Programa() {
        this.ID = ID;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }

    //Setters y getters
    public Double getID() { return ID; }
    public void setID(Double ID) { this.ID = ID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDuracion() { return duracion; }    
    public void setDuracion(String duracion) { this.duracion = duracion; }

    public Date getRegistro() { return registro; }
    public void setRegistro(Date registro) { this.registro = registro; }

    public Facultad getFacultad() { return facultad; }
    public void setFacultad(Facultad facultad) { this.facultad = facultad; }

    public String toString() {
        return "Programa{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", duracion='" + duracion + '\'' +
                ", registro=" + registro +
                ", facultad=" + facultad +
                '}';
    }
}