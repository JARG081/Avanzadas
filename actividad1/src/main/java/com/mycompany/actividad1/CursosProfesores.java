package com.mycompany.actividad1;

import java.util.ArrayList;
import java.util.List;

public class CursosProfesores {
    private List<CursoProfesor> cursosProfesores;

    public CursosProfesores() {
        cursosProfesores = new ArrayList<>();
    }
    
    public void inscribir(CursoProfesor cursoProfesor){
        cursosProfesores.add(cursoProfesor);
        System.out.println("CursoProfesor inscrito: " + cursoProfesor.getCurso().getNombre() + " " + cursoProfesor.getProfesor().getNombres() + " " + cursoProfesor.getProfesor().getApellidos());
    }

    public void guardarInformacion(CursoProfesor cursoProfesor){
        //guardar en la BD
    }

    public String toString() {
        return "CursosProfesores{" +
                "cursosProfesores=" + cursosProfesores +
                '}';
    }
    
    public void cargarDatos(){
        //no se que hace esta función
    }

    
}