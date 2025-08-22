package com.mycompany.actividad1;

public class CursoProfesor {
    private Profesor profesor;
    private int año;
    private int semestre;
    private Curso curso;

    public CursoProfesor() {
        this.profesor = profesor;
        this.año = año;
        this.semestre = semestre;
        this.curso = curso;
    }

    //Setters y getters
    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    @Override
    public String toString() {
        return "CursoProfesor{" +
                "profesor=" + profesor +
                ", año=" + año +
                ", semestre=" + semestre +
                ", curso=" + curso +
                '}';
    }
}
