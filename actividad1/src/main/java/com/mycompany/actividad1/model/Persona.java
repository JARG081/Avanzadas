package com.mycompany.actividad1.model;

import java.util.Objects;

public class Persona {
    private final Long id;          // puede ser null antes de persistir
    private final String nombres;
    private final String apellidos;
    private final String email;

    public Persona(Long id, String nombres, String apellidos, String email) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }

    // Compatibilidad con código existente que pasa Double
    public Persona(Double ID, String nombres, String apellidos, String email) {
        this(ID == null ? null : ID.longValue(), nombres, apellidos, email);
    }

    public Long getId() { return id; }
    // Getter alterno si en algún sitio llaman getID()
    public Long getID() { return id; }

    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getEmail() { return email; }

    @Override public String toString() {
        return "Persona{id=" + id + ", nombres='" + nombres + "', apellidos='" + apellidos + "', email='" + email + "'}";
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona p = (Persona) o;
        // si id es null, no las consideres iguales (aún no persistidas)
        return id != null && Objects.equals(id, p.id);
    }
    @Override public int hashCode() { return id == null ? 0 : id.hashCode(); }
}
