// src/main/java/dto/PersonaDTO.java
package dto;

public class PersonaDTO {
    private Double id;
    private String nombres;
    private String apellidos;
    private String email;

    public PersonaDTO() {}
    public PersonaDTO(Double id, String nombres, String apellidos, String email) {
        this.id = id; this.nombres = nombres; this.apellidos = apellidos; this.email = email;
    }

    public Double getId() { return id; }
    public void setId(Double id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override public String toString() {
        return "PersonaDTO{id=" + id + ", nombres='" + nombres + "', apellidos='" + apellidos + "', email='" + email + "'}";
    }
}
