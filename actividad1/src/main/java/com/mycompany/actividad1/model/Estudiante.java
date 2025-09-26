package com.mycompany.actividad1.model;

public class Estudiante extends Persona {
    private String codigo;
    private Programa programa;

    public Estudiante(Double ID, String nombres, String apellidos, String email,
                      String codigo, Programa programa) {
        super(ID, nombres, apellidos, email);
        this.codigo = codigo;
        this.programa = programa;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Programa getPrograma() { return programa; }
    public void setPrograma(Programa programa) { this.programa = programa; }

    @Override
    public String toString() {
        return "Estudiante{ID=" + getId() + ", nombres='" + getNombres() + "', apellidos='" + getApellidos()
                + "', email='" + getEmail() + "', codigo='" + codigo + "', programa='" + programa + "'}";
    }
}
