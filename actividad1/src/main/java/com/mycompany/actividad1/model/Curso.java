package com.mycompany.actividad1.model;

public class Curso {
   private Double ID;
   private String nombre;
   private Programa programa;
   private Boolean activo;

   public Curso(Double ID, String nombre, Programa programa, Boolean activo) {
      this.ID = ID;
      this.nombre = nombre;
      this.programa = programa;
      this.activo = activo;
   }

    public Curso() {
    }
    
    public Double getId() { return this.ID; } 
    
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

   public Programa getPrograma() {
      return this.programa;
   }

   public void setPrograma(Programa programa) {
      this.programa = programa;
   }

   public Boolean getActivo() {
      return this.activo;
   }

   public void setActivo(Boolean activo) {
      this.activo = activo;
   }

   public String toString() {
      return "Curso{ID=" + this.ID + ", nombre='" + this.nombre + "', programa=" + this.programa + ", activo=" + this.activo + "}";
   }
}
