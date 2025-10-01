package dto;

public class InscripcionDTO {
    private Double cursoId;
    private String cursoNombre;     
    private Double estudianteId;
    private String estudianteNombre;
    private Integer anio;
    private Integer semestre;

    public InscripcionDTO() {}
    public InscripcionDTO(Double cursoId, Double estudianteId, Integer anio, Integer semestre) {
        this.cursoId = cursoId; this.estudianteId = estudianteId;
        this.anio = anio; this.semestre = semestre;
    }

    public Double getCursoId() { return cursoId; }
    public void setCursoId(Double cursoId) { this.cursoId = cursoId; }
    public String getCursoNombre() { return cursoNombre; }
    public void setCursoNombre(String cursoNombre) { this.cursoNombre = cursoNombre; }
    public Double getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Double estudianteId) { this.estudianteId = estudianteId; }
    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String estudianteNombre) { this.estudianteNombre = estudianteNombre; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    @Override public String toString() {
        return "InscripcionDTO{cursoId="+cursoId+", curso='"+cursoNombre+
               "', estudianteId="+estudianteId+", estudiante='"+estudianteNombre+
               "', anio="+anio+", semestre="+semestre+"}";
    }
}
