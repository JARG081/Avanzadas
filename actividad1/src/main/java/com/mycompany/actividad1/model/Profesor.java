package com.mycompany.actividad1.model;

public class Profesor extends Persona {
   private String TipoContrato;

   public Profesor(Double ID, String nombres, String apellidos, String email, String tipoContrato) {
      super(ID, nombres, apellidos, email);
      this.TipoContrato = tipoContrato;
   }

   public String getTipoContrato() {
      return this.TipoContrato;
   }

   public void setTipoContrato(String TipoContrato) {
      this.TipoContrato = TipoContrato;
   }

   public String toString() {
      return "Profesor{ID=" + this.getId() + ", nombres='" + this.getNombres() + "', apellidos='" + this.getApellidos() + "', email='" + this.getEmail() + "', tipoContrato='" + this.TipoContrato + "'}";
   }
}
