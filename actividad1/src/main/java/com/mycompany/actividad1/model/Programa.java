/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.model;

/**
 *
 * @author josem
 */

public class Programa {
    private int id;
    private String nombre;
    private int facultadId;

    public Programa() {}

    public Programa(int id, String nombre, int facultadId) {
        this.id = id;
        this.nombre = nombre;
        this.facultadId = facultadId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getFacultadId() { return facultadId; }
    public void setFacultadId(int facultadId) { this.facultadId = facultadId; }
}
