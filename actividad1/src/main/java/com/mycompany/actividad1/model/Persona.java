/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.model;

/**
 *
 * @author josem
 */

public abstract class Persona {
    protected long id;
    protected String nombre;

    public Persona(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
}
