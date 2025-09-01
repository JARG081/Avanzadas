package com.mycompany.actividad1.model;
import java.sql.Date;

public class Programa {
    private Double id;
    private String nombre;
    private int duracion;
    private Date registro;
    private Facultad facultad;

    // Constructor
    public Programa(Double id, String nombre, int duracion, Date registro, Facultad facultad) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }

    public Programa() {
    }


    public Double getId() { return id; }
    public void setId(Double id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public Date getRegistro() { return registro; }
    public void setRegistro(Date registro) { this.registro = registro; }

    public Facultad getFacultad() { return facultad; }
    public void setFacultad(Facultad facultad) { this.facultad = facultad; }
    @Override
    public String toString() {
        return id + " - " + nombre;
    }

}
