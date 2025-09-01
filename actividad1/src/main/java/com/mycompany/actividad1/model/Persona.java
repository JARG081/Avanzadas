package com.mycompany.actividad1.model;

public class Persona {
   private Double id;
   private String nombres;
   private String apellidos;
   private String email;

   public Persona(Double id, String nombres, String apellidos, String email) {
      this.id = id;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.email = email;
   }

    public Persona() {
    }

   public Double getId() {
      return this.id;
   }

   public void setId(Double id) {
      this.id = id;
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

   @Override
   public String toString() {
      return "Persona: ID=" + this.id + ", nombres='" + this.nombres + "', apellidos='" + this.apellidos + "', email='" + this.email + "'";
   }
}
