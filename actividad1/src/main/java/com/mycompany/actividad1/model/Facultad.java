package com.mycompany.actividad1.model;

public class Facultad {
   private Double ID;
   private String nombre;
   private Persona decano;

   public Facultad(Double ID, String nombre, Persona decano) {
      this.ID = ID;
      this.nombre = nombre;
      this.decano = decano;
   }

   public Double getID() {
      return this.ID;
   }

   public void setID(Double ID) {
      this.ID = ID;
   }

   public String getNombre() {
      return this.nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public Persona getDecano() {
      return this.decano;
   }

   public void setDecano(Persona decano) {
      this.decano = decano;
   }

   public String toString() {
      return "Facultad{ID=" + this.ID + ", nombre='" + this.nombre + "', decano=" + this.decano + "}";
   }
}
