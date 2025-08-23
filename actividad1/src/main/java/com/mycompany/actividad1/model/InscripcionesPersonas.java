package com.mycompany.actividad1.model;

import java.util.ArrayList;
import java.util.List;

public class InscripcionesPersonas {
   private List<Persona> personas = new ArrayList<>();

   public void inscribir(Persona persona) {
      this.personas.add(persona);
      System.out.println("Persona inscrita: " + persona.getNombres() + " " + persona.getApellidos());
   }

   public void eliminar(Persona persona) {
      this.personas.remove(persona);
      System.out.println("Persona eliminada: " + persona.getNombres() + " " + persona.getApellidos());
   }

   public void actualizar(Persona persona) {
      this.personas.set(this.personas.indexOf(persona), persona);
      System.out.println("Persona actualizada: " + persona.getNombres() + " " + persona.getApellidos());
   }

   public void guardarInformacion() {
      System.out.println("Informacion guardada");
   }

   public void cargarDatos() {
      System.out.println("Datos cargados");
   }
}
