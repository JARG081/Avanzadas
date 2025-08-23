package com.mycompany.actividad1.model;

import java.util.ArrayList;
import java.util.List;

public class CursosInscritos implements Servicios {
   private List<Inscripcion> inscripciones = new ArrayList<>();

   public void inscribirCurso(Inscripcion inscripcion) {
      this.inscripciones.add(inscripcion);
      System.out.println("Curso inscrito: " + inscripcion.getCurso().getNombre());
   }

   public void eliminar(Inscripcion inscripcion) {
      this.inscripciones.remove(inscripcion);
      System.out.println("Curso eliminado: " + inscripcion.getCurso().getNombre());
   }

   public void actualizar(Inscripcion inscripcion) {
      this.inscripciones.set(this.inscripciones.indexOf(inscripcion), inscripcion);
      System.out.println("Curso actualizado: " + inscripcion.getCurso().getNombre());
   }

   public void guardarInformacion(Inscripcion inscripcion) {
      System.out.println("Informacion guardada");
   }

   public String toString() {
      return "CursosInscritos{inscripciones=" + String.valueOf(this.inscripciones) + "}";
   }

   public void cargarDatos() {
      System.out.println("Datos cargados");
   }


   // Implementación de la interfaz Servicios
   private List<String> cursos = new ArrayList<>();

    @Override
    public String imprimirPosicion(int posicion) {
        if (posicion >= 0 && posicion < cursos.size()) {
            return cursos.get(posicion);
        }
        return "Posición inválida";
    }

    @Override
    public Integer cantidadActual() {
        return cursos.size();
    }

    @Override
    public List<String> imprimirListado() {
        return cursos;
    }
}
