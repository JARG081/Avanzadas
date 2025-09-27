package com.mycompany.actividad1.model;

public class CursosInscritos {
    private Double cursoId;
    private Double estudianteId;
    private Integer anio;
    private Integer semestre;

    private String cursoNombre;
    private String estudianteNombre;

    public Double getCursoId() { return cursoId; }
    public void setCursoId(Double v) { this.cursoId = v; }

    public Double getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Double v) { this.estudianteId = v; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer v) { this.anio = v; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer v) { this.semestre = v; }

    public String getCursoNombre() { return cursoNombre; }
    public void setCursoNombre(String v) { this.cursoNombre = v; }

    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String v) { this.estudianteNombre = v; }
}
