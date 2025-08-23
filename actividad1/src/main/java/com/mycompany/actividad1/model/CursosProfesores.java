package com.mycompany.actividad1.model;

import java.util.ArrayList;
import java.util.List;

public class CursosProfesores implements Servicios {
   private List<CursoProfesor> cursosProfesores = new ArrayList<>();

   public void inscribir(CursoProfesor cursoProfesor) {
      this.cursosProfesores.add(cursoProfesor);
      System.out.println("CursoProfesor inscrito: " + cursoProfesor.getCurso().getNombre() + " " + cursoProfesor.getProfesor().getNombres() + " " + cursoProfesor.getProfesor().getApellidos());
   }

   public void guardarInformacion(CursoProfesor cursoProfesor) {
      System.out.println("Informacion guardada");
   }

   public String toString() {
      return "CursosProfesores{cursosProfesores=" + String.valueOf(this.cursosProfesores) + "}";
   }

   public void cargarDatos() {
      System.out.println("Datos cargados");
   }

   // Implementación de la interfaz Servicios
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
