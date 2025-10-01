package dto;

public class CursoDTO {
    private Double id;
    private String nombre;
    private Double programaId;     // opcional
    private String programaNombre; // opcional para UI
    private Boolean activo;

    public CursoDTO() {}

    public CursoDTO(Double id, String nombre, Double programaId, String programaNombre, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.programaId = programaId;
        this.programaNombre = programaNombre;
        this.activo = activo;
    }

    public Double getId() { return id; }
    public void setId(Double id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getProgramaId() { return programaId; }
    public void setProgramaId(Double programaId) { this.programaId = programaId; }

    public String getProgramaNombre() { return programaNombre; }
    public void setProgramaNombre(String programaNombre) { this.programaNombre = programaNombre; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "CursoDTO{id=" + id + ", nombre='" + nombre + '\'' +
               ", programaId=" + programaId + ", programaNombre='" + programaNombre + '\'' +
               ", activo=" + activo + '}';
    }
}
