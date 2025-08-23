package com.mycompany.actividad1.model;

public class Inscripcion {
   private Curso curso;
   private int año;
   private int semestre;
   private Estudiante estudiante;

   public Inscripcion(Curso curso, int año, int semestre, Estudiante estudiante) {
      this.curso = curso;
      this.año = año;
      this.semestre = semestre;
      this.estudiante = estudiante;
   }

   public Curso getCurso() {
      return this.curso;
   }

   public void setCurso(Curso curso) {
      this.curso = curso;
   }

   public int getAño() {
      return this.año;
   }

   public void setAño(int año) {
      this.año = año;
   }

   public int getSemestre() {
      return this.semestre;
   }

   public void setSemestre(int semestre) {
      this.semestre = semestre;
   }

   public Estudiante getEstudiante() {
      return this.estudiante;
   }

   public void setEstudiante(Estudiante estudiante) {
      this.estudiante = estudiante;
   }

   public String toString() {
      return "Inscripcion{curso=" + this.curso + ", año=" + this.año + ", semestre=" + this.semestre + ", estudiante=" + this.estudiante + "}";
   }
}
