package com.mycompany.actividad1;


public class Profesor extends Persona {
    private String TipoContrato;

    public Profesor(Double ID, String nombres, String apellidos, String email, String tipoContrato) {
        super(ID, nombres, apellidos, email);
        this.TipoContrato = tipoContrato;
    }

    // Getters y Setters
    public String getTipoContrato() { return TipoContrato; }
    public void setTipoContrato(String TipoContrato) { this.TipoContrato = TipoContrato; }


    @Override
    public String toString() {
        return "Profesor{" +
                "ID=" + getID() +
                ", nombres='" + getNombres() + '\'' +
                ", apellidos='" + getApellidos() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", tipoContrato='" + TipoContrato + '\'' +
                '}';
    }
}
