package com.mycompany.actividad1;

public class Curso {
    private Double ID;
    private String nombre;
    private Programa programa;
    private Boolean activo;

    public Curso() {
        this.ID = ID;
        this.nombre = nombre;
        this.programa = programa;
        this.activo = activo;
    }

    //Setters y getters
    public Double getID() { return ID; }
    public void setID(Double ID) { this.ID = ID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String toString() {
        return "Curso{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", programa=" + programa +
                ", activo=" + activo +
                '}';
    } 
}
