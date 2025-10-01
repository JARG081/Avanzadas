package dto;

public class ProgramaDTO {
    private Double id;
    private String nombre;
    private Integer duracion;        
    private String registro;         
    private Double idFacultad;       
    private String facultadNombre;  

    public ProgramaDTO() {}
    public ProgramaDTO(Double id, String nombre, Integer duracion, String registro, Double idFacultad) {
        this.id=id; this.nombre=nombre; this.duracion=duracion; this.registro=registro; this.idFacultad=idFacultad;
    }

    public Double getId() { return id; }
    public void setId(Double id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
    public String getRegistro() { return registro; }
    public void setRegistro(String registro) { this.registro = registro; }
    public Double getIdFacultad() { return idFacultad; }
    public void setIdFacultad(Double idFacultad) { this.idFacultad = idFacultad; }
    public String getFacultadNombre() { return facultadNombre; }
    public void setFacultadNombre(String facultadNombre) { this.facultadNombre = facultadNombre; }

    @Override public String toString() {
        return "ProgramaDTO{id="+id+", nombre='"+nombre+"', duracion="+duracion+
               ", registro='"+registro+"', idFacultad="+idFacultad+
               ", facultadNombre='"+facultadNombre+"'}";
    }
}
