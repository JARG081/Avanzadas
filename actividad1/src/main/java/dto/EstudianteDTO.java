// src/main/java/dto/EstudianteDTO.java
package dto;

public class EstudianteDTO {
    private Double idPersona;
    private String codigo;
    private Double idPrograma;       // opcional
    private String programaNombre;   // opcional (para mostrar)
    private String nombres;          // opcional (persona)
    private String apellidos;        // opcional
    private String email;            // opcional

    public EstudianteDTO() {}
    public EstudianteDTO(Double idPersona, String codigo, Double idPrograma) {
        this.idPersona = idPersona; this.codigo = codigo; this.idPrograma = idPrograma;
    }

    public Double getIdPersona() { return idPersona; }
    public void setIdPersona(Double idPersona) { this.idPersona = idPersona; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Double getIdPrograma() { return idPrograma; }
    public void setIdPrograma(Double idPrograma) { this.idPrograma = idPrograma; }
    public String getProgramaNombre() { return programaNombre; }
    public void setProgramaNombre(String programaNombre) { this.programaNombre = programaNombre; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override public String toString() {
        return "EstudianteDTO{idPersona="+idPersona+", codigo='"+codigo+
               "', idPrograma="+idPrograma+", programaNombre='"+programaNombre+
               "', nombres='"+nombres+"', apellidos='"+apellidos+
               "', email='"+email+"'}";
    }
}
