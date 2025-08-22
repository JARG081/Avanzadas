package com.mycompany.actividad1.model;

public class Estudiante extends Persona {
    private long programaId;

    public Estudiante(long id, String nombres, String apellidos, String email, long programaId) {
        super(id, nombres, apellidos, email);
        this.programaId = programaId;
    }

    public long getProgramaId() {
        return programaId;
    }

    public void setProgramaId(long programaId) {
        this.programaId = programaId;
    }
}
