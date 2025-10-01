package dto;

public class ProfesorDTO {
    private Double idPersona;
    private String contrato;
    private String nombres;
    private String apellidos;

    public ProfesorDTO() {}
    public ProfesorDTO(Double idPersona, String contrato) {
        this.idPersona = idPersona; this.contrato = contrato;
    }

    public Double getIdPersona() { return idPersona; }
    public void setIdPersona(Double idPersona) { this.idPersona = idPersona; }
    public String getContrato() { return contrato; }
    public void setContrato(String contrato) { this.contrato = contrato; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    @Override public String toString() {
        return "ProfesorDTO{idPersona="+idPersona+", contrato='"+contrato+"'"+
               (nombres!=null? ", nombres='"+nombres+"'":"")+
               (apellidos!=null? ", apellidos='"+apellidos+"'":"")+"}";
    }
}
