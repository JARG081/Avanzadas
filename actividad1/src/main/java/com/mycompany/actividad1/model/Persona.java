package com.mycompany.actividad1.model;

public class Persona {

    private Double id;        
    private String nombres;
    private String apellidos;
    private String email;

    public Persona(Double id, String nombres, String apellidos, String email) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }
    public Persona(){}


    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.isBlank() && !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombres='" + (nombres == null ? "" : nombres) + '\'' +
                ", apellidos='" + (apellidos == null ? "" : apellidos) + '\'' +
                ", email='" + (email == null ? "" : email) + '\'' +
                '}';
    }
}
