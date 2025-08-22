/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.actividad1.model;

/**
 *
 * @author josem
 */

public class CursoProfesor {
    private int id;
    private int cursoId;
    private int profesorId;

    public CursoProfesor() {}

    public CursoProfesor(int id, int cursoId, int profesorId) {
        this.id = id;
        this.cursoId = cursoId;
        this.profesorId = profesorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCursoId() { return cursoId; }
    public void setCursoId(int cursoId) { this.cursoId = cursoId; }

    public int getProfesorId() { return profesorId; }
    public void setProfesorId(int profesorId) { this.profesorId = profesorId; }
}
