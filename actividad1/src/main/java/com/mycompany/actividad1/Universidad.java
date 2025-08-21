package com.mycompany.actividad1;

import java.util.ArrayList;
import java.util.List;

public class Universidad {
    private String nombre;
    private List<Curso> cursos;

    public Universidad(String nombre) {
        this.nombre = nombre;
        this.cursos = new ArrayList<>();
    }

    public void agregarCurso(Curso curso) {
        cursos.add(curso);
    }

    public void mostrarCursos() {
        System.out.println("Cursos en la universidad " + nombre + ":");
        for (Curso c : cursos) {
            System.out.println(c);
        }
    }
}
