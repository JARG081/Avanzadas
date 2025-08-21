package com.mycompany.actividad1;


import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nombre;
    private Profesor profesor;
    private List<Estudiante> estudiantes;
    private Asignatura asignatura;

    public Curso(String nombre, Profesor profesor, Asignatura asignatura) {
        this.nombre = nombre;
        this.profesor = profesor;
        this.asignatura = asignatura;
        this.estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante e) {
        estudiantes.add(e);
    }

    public void mostrarEstudiantes() {
        System.out.println("Estudiantes en el curso " + nombre + ":");
        for (Estudiante e : estudiantes) {
            System.out.println(" - " + e.getNombre());
        }
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nombre='" + nombre + '\'' +
                ", profesor=" + profesor +
                ", asignatura=" + asignatura +
                ", estudiantes=" + estudiantes.size() +
                '}';
    }
}
