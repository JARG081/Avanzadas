package com.mycompany.actividad1;

public class Persona {
    protected Double ID;
    protected String nombres;
    protected String apellidos;
    protected String email;

    public Persona(Double ID, String nombres, String apellidos, String email) {
        this.ID = ID;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }

   //Getters
   public Double getID() {
    return ID;
   }

   public String getNombres() {
    return nombres;
   }

   public String getApellidos() {
    return apellidos;
   }

   public String getEmail() {
    return email;
   }

   //Setters
   public void setID(Double ID) {
    this.ID = ID;
   }

   public void setNombres(String nombres) {
    this.nombres = nombres;
   }

   public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
   }

   public void setEmail(String email) {
    this.email = email;
   }

   @Override
   public String toString() {
    return "Persona{" +
            "ID=" + ID +
            ", nombres='" + nombres + '\'' +
            ", apellidos='" + apellidos + '\'' +
            ", email='" + email + '\'' +
            '}';
   }
}
