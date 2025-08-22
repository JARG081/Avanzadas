/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.model;

/**
 *
 * @author josem
 */

public class Curso {
    private int id;
    private String nombre;
    private int programaId;

    public Curso() {}

    public Curso(int id, String nombre, int programaId) {
        this.id = id;
        this.nombre = nombre;
        this.programaId = programaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getProgramaId() { return programaId; }
    public void setProgramaId(int programaId) { this.programaId = programaId; }
}
