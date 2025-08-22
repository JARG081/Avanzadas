package model;

public class Facultad {
    private Double ID;
    private String nombre;
    private Persona decano;

    public Facultad() {
        this.ID = ID;
        this.nombre = nombre;
        this.decano = decano;
    }

    //Setters y getters
    public Double getID() { return ID; }
    public void setID(Double ID) { this.ID = ID; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Persona getDecano() { return decano; }
    public void setDecano(Persona decano) { this.decano = decano; }

    public String toString() {
        return "Facultad{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", decano=" + decano +
                '}';
    }
}
