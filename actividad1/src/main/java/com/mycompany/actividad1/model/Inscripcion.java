package com.mycompany.actividad1.model;

import java.util.Objects;

/**
 * PK compuesta: (curso.id, estudiante.id, anio, semestre)
 */
public class Inscripcion {
    private Curso curso;
    private Integer anio;
    private Integer semestre;
    private Estudiante estudiante;

    public Inscripcion() {}

    public Inscripcion(Curso curso, Integer anio, Integer semestre, Estudiante estudiante) {
        this.curso = curso;
        this.anio = anio;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    @Override public String toString() {
        String cursoStr = (curso == null) ? "(sin curso)" : (curso.getNombre() + " #" + curso.getID());
        String estStr = (estudiante == null) ? "(sin estudiante)" :
                (estudiante.getNombres() + " " + estudiante.getApellidos() + " [" + estudiante.getId() + "]");
        return "Inscripcion{curso=" + cursoStr + ", anio=" + anio +
               ", semestre=" + semestre + ", estudiante=" + estStr + "}";
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion)) return false;
        Inscripcion that = (Inscripcion) o;
        Double cursoId = (curso == null ? null : curso.getID());//double no puede ser int
        Double estId = (estudiante == null ? null : estudiante.getId());
        Double cursoId2 = (that.curso == null ? null : that.curso.getID());//double no puede ser int
        Double estId2 = (that.estudiante == null ? null : that.estudiante.getId());
        return Objects.equals(cursoId, cursoId2)
            && Objects.equals(estId, estId2)
            && Objects.equals(anio, that.anio)
            && Objects.equals(semestre, that.semestre);
    }
    @Override public int hashCode() {
        Double cursoId = (curso == null ? null : curso.getID());
        Double estId = (estudiante == null ? null : estudiante.getId());
        return Objects.hash(cursoId, estId, anio, semestre);
    }
}
