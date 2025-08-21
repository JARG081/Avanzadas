package com.mycompany.actividad1;


public class Matricula {
    private Estudiante estudiante;
    private Curso curso;

    public Matricula(Estudiante estudiante, Curso curso) {
        this.estudiante = estudiante;
        this.curso = curso;
        curso.agregarEstudiante(estudiante);
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "estudiante=" + estudiante.getNombre() +
                ", curso=" + curso +
                '}';
    }
}
