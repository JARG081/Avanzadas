package com.mycompany.actividad1.model;

public class Persona {
   private Double ID;
   private String nombres;
   private String apellidos;
   private String email;

   public Persona(Double ID, String nombres, String apellidos, String email) {
      this.ID = ID;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.email = email;
   }

   public Double getID() {
      return this.ID;
   }

   public void setID(Double ID) {
      this.ID = ID;
   }

   public String getNombres() {
      return this.nombres;
   }

   public void setNombres(String nombres) {
      this.nombres = nombres;
   }

   public String getApellidos() {
      return this.apellidos;
   }

   public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String toString() {
      return "Persona{ID=" + this.ID + ", nombres='" + this.nombres + "', apellidos='" + this.apellidos + "', email='" + this.email + "'}";
   }
}
