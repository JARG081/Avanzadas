package com.mycompany.actividad1.model;

public class Persona {

    private Double id;         // Ojo: mantengo Double porque así lo estás usando en el proyecto
    private String nombres;
    private String apellidos;
    private String email;

    // Constructor “tolerante”: NO valida duro (para no romper al leer datos viejos desde la BD)
    public Persona(Double id, String nombres, String apellidos, String email) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email; // sin throws aquí
    }

    // Getters / Setters
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

    // Setter con validación “suave”: solo valida si viene algo no vacío
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
