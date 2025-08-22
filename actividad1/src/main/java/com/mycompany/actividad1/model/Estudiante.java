/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.model;

/**
 *
 * @author josem
 */

public class Estudiante extends Persona {
    private long programaId;

    public Estudiante(long id, String nombre, long programaId) {
        super(id, nombre);
        this.programaId = programaId;
    }

    public long getProgramaId() { return programaId; }
}
