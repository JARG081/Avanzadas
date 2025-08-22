/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.model;

/**
 *
 * @author josem
 */
public class Inscripcion {
    private int id;
    private int estudianteId;
    private int cursoId;

    public Inscripcion() {}

    public Inscripcion(int id, int estudianteId, int cursoId) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEstudianteId() { return estudianteId; }
    public void setEstudianteId(int estudianteId) { this.estudianteId = estudianteId; }

    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }
}

