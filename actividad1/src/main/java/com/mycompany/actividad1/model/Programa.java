// Source code is decompiled from a .class file using FernFlower decompiler.
package com.mycompany.actividad1.model;

import java.util.Date;
    
public class Programa {
   private Double ID;
   private String nombre;
   private String duracion;
   private Date registro;
   private Facultad facultad;

   public Programa(Double ID, String nombre, String duracion, Date registro, Facultad facultad) {
      this.ID = ID;
      this.nombre = nombre;
      this.duracion = duracion;
      this.registro = registro;
      this.facultad = facultad;
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

   public String getDuracion() {
      return this.duracion;
   }

   public void setDuracion(String duracion) {
      this.duracion = duracion;
   }

   public Date getRegistro() {
      return this.registro;
   }

   public void setRegistro(Date registro) {
      this.registro = registro;
   }

   public Facultad getFacultad() {
      return this.facultad;
   }

   public void setFacultad(Facultad facultad) {
      this.facultad = facultad;
   }

   public String toString() {
      return "Programa{ID=" + this.ID + ", nombre='" + this.nombre + "', duracion='" + this.duracion + "', registro=" + this.registro + ", facultad=" + this.facultad + "}";
   }
}
