package com.mycompany.actividad1;

import java.util.ArrayList;
import java.util.List;

public class CursosProfesores {
    //Metodos propios de la clase
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

    //Métodos de la interfaz
    private List<String> profesores = new ArrayList<>();

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < profesores.size()) {
            return profesores.get(posicion);
        }
        return "Posición inválida";
    }

    @Override
    public Integer cantidadActual() {
        return profesores.size();
    }

    @Override
    public List<String> imprimirListado() {
        return profesores;
    }
}