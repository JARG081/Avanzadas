package com.mycompany.actividad1;

public class Estudiante {
    private String nombre;
    private int edad;
    private String codigo;

    public Estudiante(String nombre, int edad, String codigo) {
        this.nombre = nombre;
        this.edad = edad;
        this.codigo = codigo;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
