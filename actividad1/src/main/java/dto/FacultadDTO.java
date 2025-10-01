package dto;

public class FacultadDTO {
    private Double id;
    private String nombre;
    private Double decanoId;        
    private String decanoNombre;

    public FacultadDTO() {}
    public FacultadDTO(Double id, String nombre, Double decanoId) {
        this.id=id; this.nombre=nombre; this.decanoId=decanoId;
    }

    public Double getId() { return id; }
    public void setId(Double id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getDecanoId() { return decanoId; }
    public void setDecanoId(Double decanoId) { this.decanoId = decanoId; }
    public String getDecanoNombre() { return decanoNombre; }
    public void setDecanoNombre(String decanoNombre) { this.decanoNombre = decanoNombre; }

    @Override public String toString() {
        return "FacultadDTO{id="+id+", nombre='"+nombre+"', decanoId="+decanoId+", decanoNombre='"+decanoNombre+"'}";
    }
}
