package com.mycompany.actividad1.model;

public class Estudiante extends Persona {
   private String codigo;
   private Programa programa;

   public Estudiante(Double ID, String nombres, String apellidos, String email, String codigo, Programa programa) {
      super(ID, nombres, apellidos, email);
      this.codigo = codigo;
      this.programa = programa;
   }

   public String getCodigo() {
      return this.codigo;
   }

   public void setCodigo(String codigo) {
      this.codigo = codigo;
   }

   public Programa getPrograma() {
      return this.programa;
   }

   public void setPrograma(Programa programa) {
      this.programa = programa;
   }

   public String toString() {
      return "Estudiante{ID=" + this.getID() + ", nombres='" + this.getNombres() + "', apellidos='" + this.getApellidos() + "', email='" + this.getEmail() + "', codigo='" + this.codigo + "', programa='" + this.programa + "'}";
   }
}
